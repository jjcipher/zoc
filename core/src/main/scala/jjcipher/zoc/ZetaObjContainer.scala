package jjcipher.zoc

import java.lang.reflect.Field

import jjcipher.zoc.ZetaObjContainer._
import jjcipher.zoc.ZocException._
import jjcipher.zoc.annotation._

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.reflect.ClassTag

@SerialVersionUID(100L)
final class ZetaObjContainer[T <: ZetaObj] private(val maxSize: Int,
                                                   idMap: Map[Long, ZetaObjWrapper[T]],
                                                   uuidMap: Map[String, Long],
                                                   priList: List[Long],
                                                   mTag: String,
                                                   nextId: Long)
                                                  (implicit tag: ClassTag[T])
  extends Serializable {

  require(tag != ClassTag.Nothing, "Type parameter is required!")
  require(maxSize >= MIN_SIZE && maxSize <= MAX_SIZE, "maxSize is invalid!")

  val clz: Class[_] = tag.runtimeClass

  /* A HashMap containing all ZetaObj in this container, with ZetaObj ID as the key. */
  private[this] var _idMap = collection.mutable.Map[Long, ZetaObjWrapper[T]]() ++ idMap

  private[this] var _uuidMap = collection.mutable.Map[String, Long]() ++ uuidMap

  private[this] var _priList = collection.mutable.ListBuffer[Long]() ++ priList

  private[this] var _zocMTag: String = mTag

  private[this] var _nextId: Long = nextId

  @transient
  private[this] val lock = new Object()

  /** A flag indicating whether the ZetaObj implements WithPriority. */
  @transient
  lazy val isWithPriority = classOf[WithPriority].isAssignableFrom(clz)

  @transient
  private[zoc] lazy val checkedStringFields: Array[(Field, CheckedString)] =
    clz.getDeclaredFields
      .filter(f => f.isAnnotationPresent(classOf[CheckedString]) && f.getType == classOf[String])
      .map(f => (f, f.getAnnotation(classOf[CheckedString])))

  /* XXX: Only the first found Id field is picked up.
   *      It'd better to provide a Annotation Processor to enforce this limitation at compile time.
   */
  @transient
  private[zoc] lazy val idField: Option[(Field, Id)] =
  clz.getDeclaredFields
    .find(f => f.isAnnotationPresent(classOf[Id]) && f.getType == classOf[Long])
    .map(f => (f, f.getAnnotation(classOf[Id])))

  @transient
  private[zoc] lazy val createdAtField: Option[Field] =
    clz.getDeclaredFields
      .find(f => f.isAnnotationPresent(classOf[CreatedAt]) && f.getType == classOf[Long])

  @transient
  private[zoc] lazy val updatedAtField: Option[Field] =
    clz.getDeclaredFields
      .find(f => f.isAnnotationPresent(classOf[UpdatedAt]) && f.getType == classOf[Long])

  @transient
  private[zoc] lazy val uuidField: Option[Field] =
    clz.getDeclaredFields
      .find(f => f.isAnnotationPresent(classOf[Uuid]) && f.getType == classOf[String])

  /**
    * Constructs a ZetaObjContainer with the maximum size of the container specified.
    *
    * @param maxSize the maximum size of the container
    */
  def this(maxSize: Int)(implicit tag: ClassTag[T]) =
    this(maxSize, Map.empty, Map.empty, List.empty, INVALID_MTAG, MIN_ID)

  /**
    * Constructs a ZetaObjContainer with the default maximum size.
    */
  def this()(implicit tag: ClassTag[T]) = this(MAX_SIZE)

  def zocMTag: String = _zocMTag

  def size: Int = _idMap.size

  /*
   * Return a valid ID for next created ZetaObj according to the ID range configurations.
   * XXX: An assumption here is that the maxSize is smaller than the ID range. It might fall into a
   * infinite loop if that is not the case.
   */
  private def getNextId(): Long = {
    var candidate = 0L
    do {
      candidate = _nextId
      if (_nextId >= MAX_ID) _nextId = MIN_ID
      else _nextId += 1
    } while (_idMap.contains(candidate))
    candidate
  }

  private def getNextUuid(): String = {
    var candidate: String = Utils.genUUID()
    while (_uuidMap.contains(candidate)) {
      candidate = Utils.genUUID()
    }
    candidate
  }

  @throws[ZocException]
  private def validateCheckedStringAnnotations(id: Long, obj: T): Unit = {
    import CheckedString._

    for ((f, checkSettings) <- checkedStringFields) {
      f.setAccessible(true)
      val strValue = f.get(obj).asInstanceOf[String]

      val criteria = checkSettings.criteria
      criteria match {
        case NOT_NULL if strValue == null =>
          throw ZocException(ERR_CHECKED_STRING_NOT_NULL, f.getName)
        case NOT_EMPTY if strValue == null || strValue.length == 0 =>
          throw ZocException(ERR_CHECKED_STRING_NOT_EMPTY, f.getName)
        case UNIQUE if strValue == null || strValue.length == 0 =>
          throw ZocException(ERR_CHECKED_STRING_UNIQUE, f.getName)
        case UNIQUE =>
          // Check if strValue is unique...
          for (wrapper: ZetaObjWrapper[T] <- _idMap.values) {
            val existingStrValue = {
              try {
                f.get(obj).asInstanceOf[String]
              } catch {
                case t: Throwable => throw new ZocException("Parsing @CheckedString field " +
                  "failed!", t)
              }
            }

            // The strValue cannot be existing unless the ZetaObj has same ID (in case for update)
            if (strValue == existingStrValue && wrapper.id != id)
              throw ZocException(ERR_CHECKED_STRING_UNIQUE, f.getName)
          }
      }

      // Check minLength (strValue cannot be null or empty)
      val minLength = checkSettings.minLength
      if (minLength > 0) {
        // XXX: minLength implicitly requires NOT_NULL
        if (strValue == null || strValue.length < minLength)
          throw ZocException(ERR_CHECKED_STRING_MIN_LENGTH, f.getName)
      }

      if (strValue != null) {
        // Check maxLength if strValue is not null
        if (strValue.length > checkSettings.maxLength)
          throw ZocException(ERR_CHECKED_STRING_MAX_LENGTH, f.getName)

        // Check pattern if strValue is not null
        val regex = checkSettings.pattern
        if (regex.length > 0 && strValue.length > 0) {
          strValue match {
            case regex.r(_) => // Do nothing if it matches the pattern
            case _ => throw ZocException(ERR_CHECKED_STRING_PATTERN, f.getName)
          }
        }
      }
    }
  }

  @throws[ZocException]
  private def validateIdAnnotation(obj: T): Long = idField match {
    case None => getNextId()
    case Some(p) =>
      val f = p._1
      f.setAccessible(true)
      val providedId = f.get(obj).asInstanceOf[Long]

      // Use the provided ID as object ID if it is valid
      if (providedId isAvailable) providedId
      else {
        p._2.setting() match {
          case Id.AUTO =>
            // Generate a new ID and replace the provided ID
            val id = getNextId()
            f.set(obj, id)
            id
          case _ => throw ZocException(ERR_OBJ_ID_INVALID)
        }
      }
  }

  @throws[ZocException]
  private[this] def _add(obj: T): ZetaObjWrapper[T] = {
    if (size >= maxSize) throw ZocException(ERR_EXCEED_MAX_SIZE)

    // Validate the @CheckedString fields
    // XXX: Simply put INVALID_ID as the newObj's ID is OK for the 'add' case.
    //      It doesn't matter if the newObj has a valid provided ID or not.
    validateCheckedStringAnnotations(ZetaObjContainer.INVALID_ID, obj)

    // Validate the @ID field and use it as the ID of the new object
    val id = validateIdAnnotation(obj)

    if (obj.hasUuidField) {
      val uuid = getNextUuid()
      obj.setMetaField(uuidField, uuid)
      _uuidMap.put(uuid, id)
    }
    obj.setMetaField(createdAtField, now)
    obj.setMetaField(updatedAtField, now)

    val wrapper = new ZetaObjWrapper[T](id, obj)
    _idMap.put(id, wrapper)
    if (isWithPriority) _priList.insert(0, id)
    wrapper
  }

  @throws[ZocException]
  def add(obj: T): ZetaObjWrapper[T] = {
    lock.synchronized {
      val wrapper = _add(obj)
      _zocMTag = Utils.genMTag()
      wrapper
    }
  }

  @throws[ZocException]
  private def rollbackable[R](func: () => R): R = {
    lock.synchronized {
      // Make a copy of internal contents (_idMap, _uuidMap, _priList, _nextId)
      // in case we need to rollback if there is an error.
      val oriIdMap = _idMap
      val oriUuidMap = _uuidMap
      val oriPriorityList = _priList.clone
      val oriNextId = _nextId
      val oriZocMTag = _zocMTag

      try {
        func()
      } catch {
        case e: ZocException =>
          _idMap = oriIdMap
          _uuidMap = oriUuidMap
          _priList = oriPriorityList
          _nextId = oriNextId
          _zocMTag = oriZocMTag
          throw e
      }
    }
  }

  @throws[ZocException]
  def addBatch(objList: List[T]): List[ZetaObjWrapper[T]] = {
    rollbackable(() => {
      val results = new ArrayBuffer[ZetaObjWrapper[T]]
      for (obj <- objList) {
        val newWrapper = _add(obj)
        results.append(newWrapper)
      }
      _zocMTag = Utils.genMTag()
      results.toList
    })
  }

  @throws[ZocException]
  def update(id: Long, obj: T): ZetaObjWrapper[T] = {
    // Ensure the id is valid
    if (!id.isValid) throw ZocException(ERR_OBJ_ID_INVALID)

    // Ensure the provided ID is consistent with the given zoc id
    if (obj.hasIdField)
      if (id != obj.providedId) throw ZocException(ERR_OBJ_ID_INCONSISTENT)

    lock.synchronized {
      val oldWrapper = _idMap.get(id) match {
        case None => throw ZocException(ERR_OBJ_ID_NOT_EXIST)
        case Some(w) => w
      }

      // Validate the @CheckedString fields
      validateCheckedStringAnnotations(id, obj)

      // Ensure uuid is consistent if Uuid field is available
      if (obj.hasUuidField) {
        if (obj.uuid != oldWrapper.ref.uuid) throw ZocException(ERR_OBJ_ID_INCONSISTENT)
      }

      obj.setMetaField(updatedAtField, now)
      //val newWrapper = oldWrapper.update(obj)
      val newWrapper = new ZetaObjWrapper[T](oldWrapper.id, obj)
      _idMap.put(id, newWrapper)
      _zocMTag = Utils.genMTag()
      newWrapper
    }
  }

  @throws[ZocException]
  private[zoc] def update(obj: T): ZetaObjWrapper[T] = {
    if (obj.providedId isValid) update(obj.providedId, obj)
    else if (obj.hasUuidField) {
      val id = _uuidMap.get(obj.uuid) match {
        case Some(l) => l
        case None => throw new ZocException(ERR_OBJ_UUID_NOT_EXIST)
      }
      update(id, obj)
    } else throw new ZocException(ERR_SHOULD_NOT_HAPPEN, s"Class $tag does not support update(T)")
  }

  @throws[ZocException]
  private[this] def _delete(id: Long): ZetaObjWrapper[T] = {
    val deletedWrapper = _idMap.remove(id) match {
      case None => throw ZocException(ERR_OBJ_ID_NOT_EXIST, s"ID $id does not exist!")
      case Some(w) => w
    }
    if (deletedWrapper.ref.hasUuidField) _uuidMap.remove(deletedWrapper.ref.uuid)
    if (isWithPriority) _priList -= id
    deletedWrapper
  }

  @throws[ZocException]
  def delete(id: Long): ZetaObjWrapper[T] = {
    lock.synchronized {
      val deletedWrapper = _delete(id)
      _zocMTag = Utils.genMTag()
      deletedWrapper
    }
  }

  @throws[ZocException]
  private[zoc] def delete(uuid: String): ZetaObjWrapper[T] = {
    lock.synchronized {
      val id = _uuidMap.get(uuid) match {
        case Some(l) => l
        case _ => throw new ZocException(ERR_OBJ_UUID_NOT_EXIST)
      }

      val deletedWrapper = _delete(id)
      _zocMTag = Utils.genMTag()
      deletedWrapper
    }
  }

  @throws[ZocException]
  def deleteBatch(idList: List[Long]): List[ZetaObjWrapper[T]] = {
    rollbackable(() => {
      val results = new ArrayBuffer[ZetaObjWrapper[T]]
      for (id <- idList) {
        val oldWrapper = _delete(id)
        results.append(oldWrapper)
      }
      _zocMTag = Utils.genMTag()
      results.toList
    })
  }

  @throws[ZocException]
  def moveUp(id: Long): Unit = {
    if (isWithPriority)
      lock.synchronized {
        val pos = _priList.indexOf(id)
        if (pos != 0) {
          _priList.remove(pos)
          _priList.insert(pos - 1, id)
          _zocMTag = Utils.genMTag()
        }
      }
  }

  @throws[ZocException]
  def moveDown(id: Long): Unit = {
    if (isWithPriority)
      lock.synchronized {
        val pos = _priList.indexOf(id)
        if (pos != _priList.size - 1) {
          _priList.remove(pos)
          _priList.insert(pos + 1, id)
          _zocMTag = Utils.genMTag()
        }
      }
  }

  @throws[ZocException]
  def moveTo(id: Long, newPos: Int): Unit = {
    if (isWithPriority) {
      if (newPos < 0 || newPos > _priList.size - 1)
        throw ZocException(ERR_INPUT_PRIORITY_INVALID, "Priority (" + newPos + ") is invalid!")

      lock.synchronized {
        val pos = _priList.indexOf(id)
        if (pos != newPos) {
          _priList.remove(pos)
          _priList.insert(newPos, id)
          _zocMTag = Utils.genMTag()
        }
      }
    }
  }

  def filter(p: (ZetaObjWrapper[T]) => Boolean): Stream[ZetaObjWrapper[T]] =
    _idMap.values.toStream.filter(p)

  def find(p: (ZetaObjWrapper[T]) => Boolean): Option[ZetaObjWrapper[T]] =
    _idMap.values.toStream.find(p)

  def findById(id: Long): Option[ZetaObjWrapper[T]] = _idMap.get(id)

  @throws[ZocException]
  private[zoc] def findByUuid(uuid: String): Option[ZetaObjWrapper[T]] = _uuidMap.get(uuid) match {
    case None => None
    case Some(l) => findById(l)
  }

  @throws[ZocException]
  private[zoc] def checkZocMTag(zocMTag: String): Unit = {
    if (!this.zocMTag.equals(zocMTag)) throw new ZocException(ZocException.ERR_MTAG_MISMATCH)
  }

  def clear(): Unit = {
    lock.synchronized {
      _idMap.clear()
      _uuidMap.clear()
      _priList.clear()
      _zocMTag = INVALID_MTAG
      _nextId = INVALID_ID
    }
  }

  /*
   * Constructs a ZetaObjContainer with all states, except the transient fields, set to the given
   * source ZetaObjContainer.
   */
  def duplicate(): ZetaObjContainer[T] = {
    lock.synchronized {
      val zoc: ZetaObjContainer[T] = new ZetaObjContainer[T](this.maxSize,
        this._idMap.toMap, this._uuidMap.toMap, this._priList.toList,
        this._zocMTag, this._nextId)
      zoc
    }
  }

  private implicit class MetaFieldHelper(obj: T) {
    def providedId: Long = idField match {
      case None => INVALID_ID
      case Some(p) =>
        val f = p._1
        f.setAccessible(true)
        f.get(obj).asInstanceOf[Long]
    }

    def uuid: String = uuidField match {
      case None => INVALID_UUID
      case Some(f) =>
        f.setAccessible(true)
        f.get(obj).asInstanceOf[String]
    }

    def hasIdField: Boolean = idField match {
      case None => false
      case Some(_) => true
    }

    def hasUuidField: Boolean = uuidField match {
      case None => false
      case Some(_) => true
    }

    /**
      * Sets the value of the given metadata field.
      *
      * @param field the metadata field to set
      * @param v     new value of the metadata field
      */
    def setMetaField(field: Option[Field], v: => Any): Unit = field match {
      case Some(f) =>
        f.setAccessible(true)
        f.set(obj, v)
      case None =>
    }
  }

  private implicit class IdValidator(id: Long) {
    def isValid: Boolean = id >= MIN_ID && id <= MAX_ID
    def isAvailable: Boolean = isValid && !_idMap.contains(id)
  }

}

object ZetaObjContainer {
  private[zoc] val INVALID_ID = 0L
  private[zoc] val MIN_ID = 1L
  private[zoc] val MAX_ID = Long.MaxValue
  private[zoc] val MIN_SIZE = 1
  private[zoc] val MAX_SIZE = Int.MaxValue
  private[zoc] val INVALID_UUID: String = ""
  private[zoc] val INVALID_MTAG: String = ""

  private def now: Long = System.currentTimeMillis / 1000L
}

