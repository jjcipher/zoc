package jjcipher.zoc

trait ZetaObjManager[T <: ZetaObj] {
  def add(obj: T): ZetaObjWrapper[T]

  def addBatch(objList: List[T]): List[ZetaObjWrapper[T]]

  //def update(obj: T): ZetaObjWrapper[T]

  def update(id: Long, obj: T): ZetaObjWrapper[T]

  //def delete(obj: T): ZetaObjWrapper[T]

  def delete(id: Long): ZetaObjWrapper[T]

  def deleteBatch(idList: List[Long]): List[ZetaObjWrapper[T]]
}
