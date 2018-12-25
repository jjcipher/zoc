package jjcipher.zoc

import jjcipher.zoc.annotation.{CheckedString, Id}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ZetaObjContainerSuite extends FunSuite {

  test("@Id(setting = Id.AUTO) (default) should work correctly") {
    try {
      class Obj (id: Long) extends ZetaObj {
        @Id val myId: Long = id
      }
      val zoc = new ZetaObjContainer[Obj]
      val anId = ZetaObjContainer.MIN_ID + 1

      val objWrapper1 = zoc.add(new Obj(anId))
      assert(objWrapper1.id == anId, "AUTO idField should use the provided ID if it's valid.")

      val objWrapper2 = zoc.add(new Obj(anId))
      assert(objWrapper2.id == ZetaObjContainer.MIN_ID, "AUTO idField should use the first valid " +
        "ID if the provided ID is not unique.")

      val objWrapper3 = zoc.add(new Obj(-1L))
      assert(objWrapper3.id == ZetaObjContainer.MIN_ID + 2, "AUTO idField should use next valid " +
        "if the provided ID is invalid.")

      val objWrapper4 = zoc.add(new Obj(3L))
      assert(objWrapper4.id == ZetaObjContainer.MIN_ID + 3, "AUTO idField should use next valid " +
        "if the provided ID is not unique.")

    } catch {
      case _: Throwable => fail()
    }
  }

  test("@Id(setting = Id.PROVIDED) should work correctly") {
    try {
      class Obj (id: Long) extends ZetaObj {
        @Id(setting = Id.PROVIDED) val myId: Long = id
      }
      val zoc = new ZetaObjContainer[Obj]
      val anId = ZetaObjContainer.MIN_ID + 1

      val objWrapper1 = zoc.add(new Obj(anId))
      assert(objWrapper1.id == anId, "Provided idField should use the provided ID if it's valid.")

      try {
        zoc.add(new Obj(anId))
        fail()
      } catch {
        case e: ZocException if e.errorId == ZocException.ERR_OBJ_ID_INVALID => // OK
        case _: Throwable => fail()
      }

      try {
        zoc.add(new Obj(-1L))
        fail()
      } catch {
        case e: ZocException if e.errorId == ZocException.ERR_OBJ_ID_INVALID => // OK
        case _: Throwable => fail()
      }

    } catch {
      case _: Throwable => fail()
    }
  }

  test("@CheckedString(criteria = CheckedString.NOT_EMPTY) should work correctly") {
    class Obj (str: String) extends ZetaObj {
      @CheckedString (criteria = CheckedString.NOT_EMPTY)
      val name: String = str
    }
    val zoc = new ZetaObjContainer[Obj]
    try {
      zoc.add(new Obj(""))
      fail
    } catch {
      case e: ZocException => assert(e.errorId == ZocException.ERR_CHECKED_STRING_NOT_EMPTY)
      case _: Throwable => fail
    }
    assert(zoc.size == 0)
  }

  test("@CheckedString(criteria = CheckedString.UNIQUE) should work correctly") {
    class Obj (str: String) extends ZetaObj {
      @CheckedString (criteria = CheckedString.UNIQUE)
      val name: String = str
    }
    val zoc = new ZetaObjContainer[Obj]
    val obj = new Obj("abc")
    try {
      zoc.add(obj)
      zoc.add(new Obj("abc"))
      fail
    } catch {
      case e: ZocException => assert(e.errorId == ZocException.ERR_CHECKED_STRING_UNIQUE)
      case _: Throwable => fail
    }
    assert(zoc.size == 1)
    assert(zoc.findById(1).get.ref == obj)
  }
}
