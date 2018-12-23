package jjcipher.zoc

import jjcipher.zoc.annotation.Id
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ZetaObjContainerSuite extends FunSuite {

  test("WithPriority can only be applied with ZetaObj") {
    assertDoesNotCompile("class A extends WithPriority")
    assertCompiles("class A extends ZetaObj with WithPriority")
    succeed
  }

  test("ZetaObjContainer constructor will throw IllegalArgumentException if the " +
    "type parameter is not provided.") {
    class Obj extends ZetaObj
    new ZetaObjContainer[Obj] // ok
    assertThrows[IllegalArgumentException] {
      new ZetaObjContainer
    }
    succeed
  }

  test("idField should work correctly") {
    try {
      class Obj (id: Long) extends ZetaObj {
        @Id val myId: Long = id
      }
      val zoc = new ZetaObjContainer[Obj]
      val anId = scala.util.Random.nextLong()
      val o = new Obj(anId)
      val (f, annot) = zoc.idField.get
      f.setAccessible(true)
      assert(f.getName == "myId", "name of the idField is incorrect")
      assert(f.get(o) == anId, "value of the idField is incorrect")
    } catch {
      case e: Throwable => e.printStackTrace
    }
  }
}
