package jjcipher.zoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;

public class ZetaObjContainerTest {
  protected static ExecutorService executor;

  /**
   *
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    executor = Executors.newCachedThreadPool();
  }

  /**
   *
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    executor.shutdownNow();
  }

  /**
   *
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   *
   */
  @After
  public void tearDown() throws Exception {
  }


  /**
   * Constructor should throw IllegalArgumentException if input ZetaObj is null.
   */
  @Test(expected = java.lang.IllegalArgumentException.class)
  public void test_constructor_null() {
    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(null);
    fail();
  }

  /**
   * Validates add(ZetaObj) will throw IllegalArgumentException when input object is null.
   */
  @Test(expected = java.lang.IllegalArgumentException.class)
  public void test_add_null() {
    ClassTag<MyZetaObj> tag = ClassTag$.MODULE$.apply(MyZetaObj.class);
    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(tag);
    try {
      zoc.add(null);
    } catch (ZocException e) {
      e.printStackTrace();
    }
    fail();
  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_NOT_NULL when input object is
//   * invalid, by @CheckedString(criteria = "NotNull").
//   */
//  @Test
//  public void test_add_CheckedString_NotNull() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj = new MyZetaObj(null, "a", "b", "c", "d");
//      zoc.add(obj);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_NotNull() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_NOT_NULL, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_NOT_EMPTY when input object is
//   * invalid, by @CheckedString(criteria = "NotEmpty").
//   */
//  @Test
//  public void test_add_CheckedString_NotEmpty_null() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj = new MyZetaObj("a", "b", null, "c", "d");
//      zoc.add(obj);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_NoTEmpty_null() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_NOT_EMPTY, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_NOT_EMPTY when input object is
//   * invalid, by @CheckedString(criteria = "NotEmpty").
//   */
//  @Test
//  public void test_add_CheckedString_NotEmpty_empty() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj = new MyZetaObj("a", "b", "", "c", "d");
//      zoc.add(obj);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_NoTEmpty_empty() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_NOT_EMPTY, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_UNIQUE when input object is
//   * invalid, by @CheckedString(criteria = "Unique").
//   */
//  @Test
//  public void test_add_CheckedString_Unique_null() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("a", "b", "c", "aaa@abc.com", "desc");
//      MyZetaObj obj2 = new MyZetaObj("d", null, "f", "aaa@abc.com", "desc");
//      zoc.add(obj1);
//      zoc.add(obj2);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_Unique_null() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_UNIQUE, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_UNIQUE when input object is
//   * invalid, by @CheckedString(criteria = "Unique").
//   */
//  @Test
//  public void test_add_CheckedString_Unique_empty() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("a", "b", "c", "aaa@abc.com", "desc");
//      MyZetaObj obj2 = new MyZetaObj("d", "", "f", "aaa@abc.com", "desc");
//      zoc.add(obj1);
//      zoc.add(obj2);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_Unique_empty() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_UNIQUE, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_UNIQUE when input object is
//   * invalid, by @CheckedString(criteria = "Unique").
//   */
//  @Test
//  public void test_add_CheckedString_Unique_duplicated() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("a", "b", "c", "aaa@abc.com", "desc");
//      MyZetaObj obj2 = new MyZetaObj("d", "b", "f", "aaa@abc.com", "desc");
//      zoc.add(obj1);
//      zoc.add(obj2);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_Unique_duplicated() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_UNIQUE, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_PATTERN when input object is
//   * invalid, by @CheckedString(pattern = "xxx").
//   */
//  @Test
//  public void test_add_CheckedString_pattern() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("d", "b", "f", "aaacabc.com", "desc");
//      zoc.add(obj1);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_add_CheckedString_pattern() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_PATTERN, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_MIN_LENGTH when input object is
//   * invalid, by @CheckedString(minLength = x).
//   */
//  @Test
//  public void test_add_CheckedString_minLength_null() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("d", "b", "f", null, "desc");
//      zoc.add(obj1);
//      fail();
//    } catch (ZocException e) {
//      System.out
//          .println("test_add_CheckedString_minLength_null() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_MIN_LENGTH, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_MIN_LENGTH when input object is
//   * invalid, by @CheckedString(minLength = x).
//   */
//  @Test
//  public void test_add_CheckedString_minLength_empty() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("d", "b", "f", "", "desc");
//      zoc.add(obj1);
//      fail();
//    } catch (ZocException e) {
//      System.out
//          .println("test_add_CheckedString_minLength_empty() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_MIN_LENGTH, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_MIN_LENGTH when input object is
//   * invalid, by @CheckedString(minLength = x).
//   */
//  @Test
//  public void test_add_CheckedString_minLength_length() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("d", "b", "f", "g", "desc");
//      zoc.add(obj1);
//      fail();
//    } catch (ZocException e) {
//      System.out
//          .println("test_add_CheckedString_minLength_length() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_MIN_LENGTH, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) will throw ZocException.ERR_CHECKED_STRING_MAX_LENGTH when input object is
//   * invalid, by @CheckedString(maxLength = x).
//   */
//  @Test
//  public void test_add_CheckedString_maxLength_length() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("d", "b", "f", "abc@abc.com", "12345678901");
//      zoc.add(obj1);
//      fail();
//    } catch (ZocException e) {
//      System.out
//          .println("test_add_CheckedString_maxLength_length() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_CHECKED_STRING_MAX_LENGTH, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) can accept null string field if that field is given only
//   * with @CheckedString(maxLength = x).
//   */
//  @Test
//  public void test_add_CheckedString_maxLength_null() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//    MyZetaObj obj1 = new MyZetaObj("d", "b", "f", "abc@abc.com", null);
//    try {
//      zoc.add(obj1);
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates add(ZetaObj) returns a ZetaObjWrapper with correct ZetaObj.
//   */
//  @Test
//  public void test_add_reference() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//    String name = "a";
//    String login = "b";
//    String password = "c";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObj obj1 = new MyZetaObj(name, login, password, email, description);
//    try {
//      ZetaObjWrapper<MyZetaObj> wrapper = zoc.add(obj1);
//      MyZetaObj ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(login, ref.login);
//      assertEquals(password, ref.password);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(1, zoc.size());
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates addBatch(List<ZetaObj>) returns a list of ZetaObjWrappers.
//   */
//  @Test
//  public void test_addBatch() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//
//    MyZetaObj obj1 = new MyZetaObj("aaa", "login_a", "pass_a", "aaa@test.com", "test_a");
//    MyZetaObj obj2 = new MyZetaObj("bbb", "login_b", "pass_b", "bbb@test.com", "test_b");
//
//    List<MyZetaObj> objList = new ArrayList<>(Arrays.asList(obj1, obj2));
//
//    try {
//      List<ZetaObjWrapper<MyZetaObj>> wrappers = zoc.addBatch(objList);
//
//      assertEquals(2, zoc.size());
//
//      assertEquals(obj1.name, wrappers.get(0).reference.name);
//      assertEquals(obj1.login, wrappers.get(0).reference.login);
//      assertEquals(obj1.password, wrappers.get(0).reference.password);
//      assertEquals(obj1.description, wrappers.get(0).reference.description);
//
//      assertEquals(obj2.name, wrappers.get(1).reference.name);
//      assertEquals(obj2.login, wrappers.get(1).reference.login);
//      assertEquals(obj2.password, wrappers.get(1).reference.password);
//      assertEquals(obj2.description, wrappers.get(1).reference.description);
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates addBatch(List<ZetaObj>) will rollback to the previous states if there is any error
//   * occurs.
//   */
//  @Test
//  public void test_addBatch_atomic() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//
//    MyZetaObj obj = new MyZetaObj("ooo", "login_a", "pass_a", "aaa@test.com", "test_a");
//    MyZetaObj obj1 = new MyZetaObj("aaa", "login_a", "pass_a", "aaa@test.com", "test_a");
//    MyZetaObj obj2 = new MyZetaObj("ooo", "login_b", "pass_b", "bbb@test.com", "test_b");
//
//    List<MyZetaObj> objList = new ArrayList<>(Arrays.asList(obj1, obj2));
//
//    try {
//      zoc.add(obj);
//      List<ZetaObjWrapper<MyZetaObj>> wrappers = zoc.addBatch(objList);
//      fail();
//
//    } catch (ZocException e) {
//      // Expected ZocException.ERR_CHECKED_STRING_UNIQUE
//      assertEquals(ZocException.ERR_CHECKED_STRING_UNIQUE, e.getErrorId());
//
//      assertEquals(1, zoc.size());
//
//      try {
//        MyZetaObj objInZoc = zoc.findAll().get(0).reference;
//        assertEquals(obj.name, objInZoc.name);
//        assertEquals(obj.login, objInZoc.login);
//        assertEquals(obj.password, objInZoc.password);
//        assertEquals(obj.description, objInZoc.description);
//
//      } catch (ZocException e1) {
//        fail();
//      }
//    }
//  }
//
//  /**
//   * Validates delete(long) throws throw ZocException.ERR_INPUT_OBJ_INVALID
//   */
//  @Test
//  public void test_delete_id_not_existing() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      zoc.delete(1);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_delete_id_not_exist() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates delete(long) returns a ZetaObjWrapper with correct ZetaObj reference.
//   */
//  @Test
//  public void test_delete() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//    String name = "a";
//    String login = "b";
//    String password = "c";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObj obj1 = new MyZetaObj(name, login, password, email, description);
//
//    try {
//      long id = zoc.add(obj1).id;
//
//      ZetaObjWrapper<MyZetaObj> wrapper = zoc.delete(id);
//      MyZetaObj ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(login, ref.login);
//      assertEquals(password, ref.password);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(0, zoc.size());
//
//      try {
//        zoc.findById(id);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_delete() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//      }
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates delete(long) returns a ZetaObjWrapper with correct ZetaObj reference.
//   */
//  @Test
//  public void test_delete_WithPriority() {
//    ZetaObjContainer<MyZetaObjWithPriority> zoc = new ZetaObjContainer<>(MyZetaObjWithPriority.class);
//    String name = "a";
//    MyZetaObjWithPriority obj1 = new MyZetaObjWithPriority(name);
//
//    try {
//      long id = zoc.add(obj1).id;
//
//      ZetaObjWrapper<MyZetaObjWithPriority> wrapper = zoc.delete(id);
//      MyZetaObjWithPriority ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(0, zoc.size());
//
//      try {
//        zoc.findById(id);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_delete_WithPriority() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//      }
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates delete(long) returns a ZetaObjWrapper with correct ZetaObj reference.
//   */
//  @Test
//  public void test_delete_WithMTag() {
//    ZetaObjContainer<MyZetaObjWithMTag> zoc = new ZetaObjContainer<>(MyZetaObjWithMTag.class);
//    String name = "a";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObjWithMTag obj1 = new MyZetaObjWithMTag(name, email, description);
//
//    try {
//      long id = zoc.add(obj1).id;
//
//      ZetaObjWrapper<MyZetaObjWithMTag> wrapper = zoc.delete(id);
//      MyZetaObjWithMTag ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(0, zoc.size());
//
//      try {
//        zoc.findById(id);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_delete_WithMTag() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//      }
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates deleteBatch(List<Long>) returns a list of deleted ZetaObjWrappers.
//   */
//  @Test
//  public void test_deleteBatch() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//
//    MyZetaObj obj1 = new MyZetaObj("aaa", "login_a", "pass_a", "aaa@test.com", "test_a");
//    MyZetaObj obj2 = new MyZetaObj("bbb", "login_b", "pass_b", "bbb@test.com", "test_b");
//
//    List<MyZetaObj> objList = new ArrayList<>(Arrays.asList(obj1, obj2));
//
//    try {
//      // addBatch(List<ZetaObj>) first
//      zoc.addBatch(objList);
//
//      //
//      List<Long> idList = new ArrayList<>(Arrays.asList(1L, 2L));
//      List<ZetaObjWrapper<MyZetaObj>> wrappers = zoc.deleteBatch(idList);
//
//      assertEquals(0, zoc.size());
//
//      assertEquals(obj1.name, wrappers.get(0).reference.name);
//      assertEquals(obj1.login, wrappers.get(0).reference.login);
//      assertEquals(obj1.password, wrappers.get(0).reference.password);
//      assertEquals(obj1.description, wrappers.get(0).reference.description);
//
//      assertEquals(obj2.name, wrappers.get(1).reference.name);
//      assertEquals(obj2.login, wrappers.get(1).reference.login);
//      assertEquals(obj2.password, wrappers.get(1).reference.password);
//      assertEquals(obj2.description, wrappers.get(1).reference.description);
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates deleteBatch(List<Long>) will rollback to the previous states if there is any error
//   * occurs.
//   */
//  @Test
//  public void test_deleteBatch_atomic() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//
//    MyZetaObj obj = new MyZetaObj("aaa", "login_a", "pass_a", "aaa@test.com", "test_a");
//
//    try {
//      zoc.add(obj);
//
//      // XXX: delete ID 1 & 2, even though ID 2 does not exist
//      List<Long> idList = new ArrayList<>(Arrays.asList(1L, 2L));
//      zoc.deleteBatch(idList);
//      fail();
//
//    } catch (ZocException e) {
//      // Expected ZocException.ERR_INPUT_ID_NOT_EXIST
//      assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//
//      assertEquals(1, zoc.size());
//
//      try {
//        MyZetaObj objInZoc = zoc.findAll().get(0).reference;
//        assertEquals(obj.name, objInZoc.name);
//        assertEquals(obj.login, objInZoc.login);
//        assertEquals(obj.password, objInZoc.password);
//        assertEquals(obj.description, objInZoc.description);
//
//      } catch (ZocException e1) {
//        fail();
//      }
//    }
//  }
//
//  /**
//   * Validates update(long, ZetaObj) returns a ZetaObjWrapper with correct ZetaObj reference.
//   */
//  @Test
//  public void test_update() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//    MyZetaObj obj1 = new MyZetaObj("1", "2", "3", "111@abc.com", "abcde");
//    String name = "a";
//    String login = "b";
//    String password = "c";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObj obj2 = new MyZetaObj(name, login, password, email, description);
//
//    try {
//      long id = zoc.add(obj1).id;
//
//      ZetaObjWrapper<MyZetaObj> wrapper = zoc.update(id, obj2);
//      MyZetaObj ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(login, ref.login);
//      assertEquals(password, ref.password);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(1, zoc.size());
//
//      MyZetaObj obj = zoc.findById(id).reference;
//      assertEquals(name, obj.name);
//      assertEquals(login, obj.login);
//      assertEquals(password, obj.password);
//      assertEquals(email, obj.email);
//      assertEquals(description, obj.description);
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates updating a ZetaObj which is not WithMTag through update(long, ZetaObj, String)
//   * returns a ZetaObjWrapper with correct ZetaObj reference, even the given mTag is incorrect.
//   */
//  @Test
//  public void test_update_incorrect_mTag() {
//    ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//    MyZetaObj obj1 = new MyZetaObj("1", "2", "3", "111@abc.com", "abcde");
//    String name = "a";
//    String login = "b";
//    String password = "c";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObj obj2 = new MyZetaObj(name, login, password, email, description);
//
//    try {
//      long id = zoc.add(obj1).id;
//
//      ZetaObjWrapper<MyZetaObj> wrapper = zoc.update(id, obj2, "wrongMTag");
//      MyZetaObj ref = wrapper.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(login, ref.login);
//      assertEquals(password, ref.password);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(1, zoc.size());
//
//      MyZetaObj obj = zoc.findById(id).reference;
//      assertEquals(name, obj.name);
//      assertEquals(login, obj.login);
//      assertEquals(password, obj.password);
//      assertEquals(email, obj.email);
//      assertEquals(description, obj.description);
//
//    } catch (ZocException e) {
//      fail();
//    }
//  }
//
//  /**
//   * Validates update(long, ZetaObj) throws ZocException if the ZetaObj implements WithMTag
//   */
//  @Test
//  public void test_update_WithMTag_wrong_api() {
//    ZetaObjContainer<MyZetaObjWithMTag> zoc = new ZetaObjContainer<>(MyZetaObjWithMTag.class);
//    MyZetaObjWithMTag obj1 = new MyZetaObjWithMTag("1", "111@abc.com", "abcde");
//    String name = "a";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObjWithMTag obj2 = new MyZetaObjWithMTag(name, email, description);
//
//    try {
//      long id = zoc.add(obj1).id;
//      zoc.update(id, obj2);
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_update_WithMTag_wrong_api() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_MTAG_MISMATCH, e.getErrorId());
//    }
//  }
//
//  /**
//   * Validates update(long, ZetaObj, String) throws ZocException if the ZetaObj implements WithMTag
//   * and the input mTag is wrong.
//   */
//  @Test
//  public void test_update_WithMTag_wrong_mTag() {
//    ZetaObjContainer<MyZetaObjWithMTag> zoc = new ZetaObjContainer<>(MyZetaObjWithMTag.class);
//    MyZetaObjWithMTag obj1 = new MyZetaObjWithMTag("1", "111@abc.com", "abcde");
//    String name = "a";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObjWithMTag obj2 = new MyZetaObjWithMTag(name, email, description);
//
//    try {
//      ZetaObjWrapper<MyZetaObjWithMTag> objWrapper1 = zoc.add(obj1);
//      zoc.update(objWrapper1.id, obj2, "wrongMTag");
//      fail();
//    } catch (ZocException e) {
//      System.out.println("test_update_WithMTag_wrong_mTag() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_MTAG_MISMATCH, e.getErrorId());
//    }
//  }
//
//  /**
//   * Validates update(long, ZetaObj, String) throws ZocException if the ZetaObj implements WithMTag
//   * and the input mTag is wrong.
//   */
//  @Test
//  public void test_update_WithMTag() {
//    ZetaObjContainer<MyZetaObjWithMTag> zoc = new ZetaObjContainer<>(MyZetaObjWithMTag.class);
//    MyZetaObjWithMTag obj1 = new MyZetaObjWithMTag("1", "111@abc.com", "abcde");
//    String name = "a";
//    String email = "abc@abc.com";
//    String description = "test";
//    MyZetaObjWithMTag obj2 = new MyZetaObjWithMTag(name, email, description);
//
//    try {
//      ZetaObjWrapper<MyZetaObjWithMTag> objWrapper1 = zoc.add(obj1);
//      ZetaObjWrapper<MyZetaObjWithMTag> objWrapper2 = zoc.update(objWrapper1.id, obj2, objWrapper1.getMTag());
//
//      MyZetaObjWithMTag ref = objWrapper2.reference;
//
//      assertEquals(name, ref.name);
//      assertEquals(email, ref.email);
//      assertEquals(description, ref.description);
//      assertEquals(1, zoc.size());
//
//    } catch (Exception e) {
//      e.printStackTrace();
//      fail();
//    }
//  }
//
//  /**
//   * Validates size() will correctly return the number of contained ZetaObj.
//   */
//  @Test
//  public void test_size() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("1", "1", "1", "abc@abc.com", "1234567890");
//      MyZetaObj obj2 = new MyZetaObj("2", "2", "2", "abc@abc.com", "1234567890");
//      MyZetaObj obj3 = new MyZetaObj("3", "3", "3", "abc@abc.com", "1234567890");
//
//      ZetaObjWrapper<MyZetaObj> wrapper1 = zoc.add(obj1);
//      assertEquals(zoc.size(), 1);
//
//      ZetaObjWrapper<MyZetaObj> wrapper2 = zoc.add(obj2);
//      assertEquals(zoc.size(), 2);
//
//      ZetaObjWrapper<MyZetaObj> wrapper3 = zoc.add(obj3);
//      assertEquals(zoc.size(), 3);
//
//      zoc.delete(wrapper1.id);
//      assertEquals(zoc.size(), 2);
//
//      zoc.delete(wrapper2.id);
//      assertEquals(zoc.size(), 1);
//
//      zoc.delete(wrapper3.id);
//      assertEquals(zoc.size(), 0);
//
//    } catch (Throwable t) {
//      fail();
//    }
//  }
//
//  /*
//   * Test getMaxSize() & setMaxSize(int)
//   */
//  @Test
//  public void test_setMaxSize_getMaxSize() throws Throwable {
//    Class<ZetaObj> class0 = ZetaObj.class;
//    ZetaObjContainer<ZetaObj> zoc = new ZetaObjContainer<>(class0);
//    assertEquals(2147483647, zoc.getMaxSize());
//
//    zoc.setMaxSize(1);
//    assertEquals(1, zoc.getMaxSize());
//
//    ZetaObjContainer<ZetaObj> zoc2 = new ZetaObjContainer<>(class0);
//    zoc2.setMaxSize(-1);
//    assertEquals(1, zoc.getMaxSize());
//  }
//
//  /*
//   * Test checkMaxSize(). Validate that ZocException.ERR_EXCEED_MAX_SIZE is thrown if
//   * maxSize is reached.
//   */
//  @Test
//  public void test_checkMaxSize() throws Throwable {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      zoc.setMaxSize(1);
//      MyZetaObj obj1 = new MyZetaObj("1", "1", "1", "abc@abc.com", "1234567890");
//      MyZetaObj obj2 = new MyZetaObj("2", "2", "2", "abc@abc.com", "1234567890");
//
//      zoc.add(obj1);
//      assertEquals(1, zoc.size());
//
//      zoc.add(obj2);
//      fail();
//
//    } catch (ZocException e) {
//      System.out.println("test_checkMaxSize() - error message: " + e.getMessage());
//      assertEquals(ZocException.ERR_EXCEED_MAX_SIZE, e.getErrorId());
//    }
//  }
//
//  /**
//   * Test moveUp(int), moveDown(int), and MoveTo(int, int). Validate ZocException.ERR_WITH_PRIORITY_NOT_SUPPORTED is thrown if
//   * ZetaObj does not implements WithPriority.
//   */
//  @Test
//  public void test_move_no_WithPriority() {
//    try {
//      ZetaObjContainer<MyZetaObj> zoc = new ZetaObjContainer<>(MyZetaObj.class);
//      MyZetaObj obj1 = new MyZetaObj("1", "1", "1", "abc@abc.com", "1234567890");
//      MyZetaObj obj2 = new MyZetaObj("2", "2", "2", "abc@abc.com", "1234567890");
//      MyZetaObj obj3 = new MyZetaObj("3", "3", "3", "abc@abc.com", "1234567890");
//
//      ZetaObjWrapper<MyZetaObj> wrapper1 = zoc.add(obj1);
//      ZetaObjWrapper<MyZetaObj> wrapper2 = zoc.add(obj2);
//      ZetaObjWrapper<MyZetaObj> wrapper3 = zoc.add(obj3);
//      long id1 = wrapper1.id;
//      long id2 = wrapper2.id;
//      long id3 = wrapper3.id;
//      try {
//        zoc.moveUp(id3, "");
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_moveUp_no_WithPriority() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_WITH_PRIORITY_NOT_SUPPORTED, e.getErrorId());
//      }
//
//      try {
//        zoc.moveDown(id1, "");
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_moveDown_no_WithPriority() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_WITH_PRIORITY_NOT_SUPPORTED, e.getErrorId());
//      }
//
//      try {
//        zoc.moveTo(id3, 0, "");
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_moveDown_no_WithPriority() - error message: " + e.getMessage());
//        assertEquals(ZocException.ERR_WITH_PRIORITY_NOT_SUPPORTED, e.getErrorId());
//      }
//
//    } catch (Exception e) {
//      fail();
//    }
//  }
//
//  /**
//   * Test moveUp(int), moveDown(int), MoveTo(int, int), and getPriorityById(long). Validate it
//   * works for ZetaObj implementing WithPriority.
//   */
//  @Test
//  public void test_move_WithPriority() {
//    try {
//      ZetaObjContainer<MyZetaObjWithPriority> zoc = new ZetaObjContainer<>(MyZetaObjWithPriority.class);
//      MyZetaObjWithPriority obj1 = new MyZetaObjWithPriority("1");
//      MyZetaObjWithPriority obj2 = new MyZetaObjWithPriority("2");
//      MyZetaObjWithPriority obj3 = new MyZetaObjWithPriority("3");
//
//      ZetaObjWrapper<MyZetaObjWithPriority> wrapper1 = zoc.add(obj1);
//      ZetaObjWrapper<MyZetaObjWithPriority> wrapper2 = zoc.add(obj2);
//      ZetaObjWrapper<MyZetaObjWithPriority> wrapper3 = zoc.add(obj3);
//      long id1 = wrapper1.id;
//      long id2 = wrapper2.id;
//      long id3 = wrapper3.id;
//      // order: 3, 2, 1
//      try {
//        zoc.moveUp(id1, zoc.getZocMTag());
//        // order: 3, 1, 2
//        List<ZetaObjWrapper<MyZetaObjWithPriority>> list = zoc.findAllPrioritized();
//        assertEquals("3", list.get(0).reference.name);
//        assertEquals("1", list.get(1).reference.name);
//        assertEquals("2", list.get(2).reference.name);
//
//        assertEquals(0, zoc.getPriorityById(id3));
//        assertEquals(1, zoc.getPriorityById(id1));
//        assertEquals(2, zoc.getPriorityById(id2));
//
//      } catch (ZocException e) {
//        fail();
//      }
//
//      try {
//        zoc.moveDown(id3, zoc.getZocMTag());
//        // order: 1, 3, 2
//        List<ZetaObjWrapper<MyZetaObjWithPriority>> list = zoc.findAllPrioritized();
//        assertEquals("1", list.get(0).reference.name);
//        assertEquals("3", list.get(1).reference.name);
//        assertEquals("2", list.get(2).reference.name);
//
//        assertEquals(0, zoc.getPriorityById(id1));
//        assertEquals(1, zoc.getPriorityById(id3));
//        assertEquals(2, zoc.getPriorityById(id2));
//      } catch (ZocException e) {
//        fail();
//      }
//
//      try {
//        zoc.moveTo(id2, 0, zoc.getZocMTag());
//        // order: 2, 1, 3
//        List<ZetaObjWrapper<MyZetaObjWithPriority>> list = zoc.findAllPrioritized();
//        assertEquals("2", list.get(0).reference.name);
//        assertEquals("1", list.get(1).reference.name);
//        assertEquals("3", list.get(2).reference.name);
//
//        assertEquals(0, zoc.getPriorityById(id2));
//        assertEquals(1, zoc.getPriorityById(id1));
//        assertEquals(2, zoc.getPriorityById(id3));
//      } catch (ZocException e) {
//        fail();
//      }
//
//      try {
//        zoc.moveTo(id2, 3, "wrongMTag");
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_move_WithPriority() - error message1: " + e.getMessage());
//        assertEquals(ZocException.ERR_MTAG_MISMATCH, e.getErrorId());
//      }
//
//      try {
//        zoc.moveTo(id2, 100, zoc.getZocMTag());
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_move_WithPriority() - error message2: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_PRIORITY_INVALID, e.getErrorId());
//      }
//
//      try {
//        zoc.delete(id1);
//        // order: 2, 3
//        List<ZetaObjWrapper<MyZetaObjWithPriority>> list = zoc.findAllPrioritized();
//        assertEquals("2", list.get(0).reference.name);
//        assertEquals("3", list.get(1).reference.name);
//
//        assertEquals(0, zoc.getPriorityById(id2));
//        assertEquals(1, zoc.getPriorityById(id3));
//      } catch (ZocException e) {
//        fail();
//      }
//
//      try {
//        zoc.getPriorityById(id1);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_move_WithPriority() - error message3: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//      }
//
//      try {
//        zoc.moveUp(id1, zoc.getZocMTag());
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_move_WithPriority() - error message4: " + e.getMessage());
//        assertEquals(ZocException.ERR_INPUT_ID_NOT_EXIST, e.getErrorId());
//      }
//
//    } catch (Exception e) {
//      fail();
//    }
//  }
//
//  /**
//   * Test close(), isClosed().
//   */
//  @Test
//  public void test_close() {
//    try {
//      ZetaObjContainer<MyZetaObjWithPriority> zoc = new ZetaObjContainer<>(MyZetaObjWithPriority.class);
//      MyZetaObjWithPriority obj1 = new MyZetaObjWithPriority("1");
//      MyZetaObjWithPriority obj2 = new MyZetaObjWithPriority("2");
//
//      ZetaObjWrapper<MyZetaObjWithPriority> wrapper1 = zoc.add(obj1);
//      long id1 = wrapper1.id;
//      String zocMTag = zoc.getZocMTag();
//
//      // isClosed() should returns false before close()
//      boolean isClosed = zoc.isClosed();
//      assertEquals(false, isClosed);
//
//      // isClosed() should returns true after close()
//      zoc.close();
//      isClosed = zoc.isClosed();
//      assertEquals(true, isClosed);
//
//      // ZOC properties should be cleaned up after close()
//      assertEquals(false, zoc.isWithMTag);
//      assertEquals(ZetaObjContainer.INITIAL_MTAG, zoc.getZocMTag());
//
//      // add(ZetaObj) throws ZocException.ERR_ZOC_CLOSED after close()
//      try{
//        zoc.add(obj2);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_close() - error message1: " + e.getMessage());
//        assertEquals(ZocException.ERR_ZOC_CLOSED, e.getErrorId());
//      }
//
//      // delete(long) throws ZocException.ERR_ZOC_CLOSED after close()
//      try{
//        zoc.delete(id1);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_close() - error message2: " + e.getMessage());
//        assertEquals(ZocException.ERR_ZOC_CLOSED, e.getErrorId());
//      }
//
//      // update(long, ZetaObj) throws ZocException.ERR_ZOC_CLOSED after close()
//      try{
//        zoc.update(id1, obj2);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_close() - error message3: " + e.getMessage());
//        assertEquals(ZocException.ERR_ZOC_CLOSED, e.getErrorId());
//      }
//
//      // findById(long) throws ZocException.ERR_ZOC_CLOSED after close()
//      try{
//        zoc.findById(id1);
//        fail();
//      } catch (ZocException e) {
//        System.out.println("test_close() - error message1: " + e.getMessage());
//        assertEquals(ZocException.ERR_ZOC_CLOSED, e.getErrorId());
//      }
//
//    } catch (Exception e) {
//      fail();
//    }
//
//  }


/*
  @Test
  public void test02() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    long long0 = 0L;
    try {
      zetaObjContainer0.findById(long0);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ID (0) does not exist!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test03() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    long long0 = 0L;
    try {
      zetaObjContainer0.update(0L, (ZetaObj) null, ") not suitable, must be java.lang.String");
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // no message in exception (getMessage() returned null)
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test04() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    long long0 = 0L;
    try {
      zetaObjContainer0.findById((-1L));
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ID (-1) does not exist!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test05() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    long long0 = 0L;
    try {
      zetaObjContainer0.moveTo((-877L), 0, "S2Fx5K:N|y[");
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // The contained ZetaObj does not implements WithPriority!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test06() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.findById(719L);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ID (719) does not exist!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test07() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.moveUp(0L, " and/or that the exported package is correctly bundled in ");
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // The contained ZetaObj does not implements WithPriority!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test08() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    ZetaObj zetaObj0 = null;
    try {
      zetaObjContainer0.update((-948L), (ZetaObj) null);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // no message in exception (getMessage() returned null)
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test09() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.moveDown(0L, "msS");
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // The contained ZetaObj does not implements WithPriority!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test10() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.close();
    // Undeclared exception!
    try {
      zetaObjContainer0.clone();
      fail("Expecting exception: IllegalStateException");

    } catch (IllegalStateException e) {
      //
      // ZetaObjContainer is closed!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test11() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.findAllPrioritized();
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // The contained ZetaObj does not implements WithPriority!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test12() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.close();
    try {
      zetaObjContainer0.findAll();
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ZetaObjContainer is closed!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test13() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.close();
    zetaObjContainer0.close();
    assertTrue(zetaObjContainer0.isClosed());
  }

  @Test
  public void test14() throws Throwable {
    Future<?> future = executor.submit(new Runnable() {
      @Override
      public void run() {
        try {
          Class<ZetaObj> class0 = ZetaObj.class;
          ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
          DefaultPersistenceProvider<ZetaObj> defaultPersistenceProvider0 =
              new DefaultPersistenceProvider<>("yCmr]eJ|AaMiIh8");
          zetaObjContainer0.setPersistenceProvider(defaultPersistenceProvider0);
          try {
            zetaObjContainer0.save();
            fail("Expecting exception: ZocException");

          } catch (ZocException e) {
            //
            // Security manager blocks (\"java.io.FilePermission\" \"yCmr]eJ|AaMiIh8.new\"
            // \"write\")
            // java.lang.Thread.getStackTrace(Thread.java:1552)
            // org.evosuite.runtime.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:433)
            // java.lang.SecurityManager.checkWrite(SecurityManager.java:979)
            // java.io.FileOutputStream.<init>(FileOutputStream.java:200)
            // java.io.FileOutputStream.<init>(FileOutputStream.java:101)
            // com.trendmicro.zeta.common.zoc_new.DefaultPersistenceProvider.persist(DefaultPersistenceProvider.java:107)
            // com.trendmicro.zeta.common.zoc_new.ZetaObjContainer.save(ZetaObjContainer.java:682)
            // sun.reflect.GeneratedMethodAccessor69.invoke(Unknown Source)
            // sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            // java.lang.reflect.Method.invoke(Method.java:498)
            // org.evosuite.testcase.statements.MethodStatement$1.execute(MethodStatement.java:261)
            // org.evosuite.testcase.statements.AbstractStatement.exceptionHandler(AbstractStatement.java:169)
            // org.evosuite.testcase.statements.MethodStatement.execute(MethodStatement.java:224)
            // org.evosuite.testcase.execution.TestRunnable.executeStatements(TestRunnable.java:306)
            // org.evosuite.testcase.execution.TestRunnable.call(TestRunnable.java:212)
            // org.evosuite.testcase.execution.TestRunnable.call(TestRunnable.java:55)
            // java.util.concurrent.FutureTask.run(FutureTask.java:266)
            // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
            // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
            // java.lang.Thread.run(Thread.java:745)
            //
            assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
          }
        } catch (Throwable t) {
          // Need to catch declared exceptions
        }
      }
    });
    future.get(4000, TimeUnit.MILLISECONDS);
  }

  @Test
  public void test15() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.save();
    assertEquals("0", zetaObjContainer0.getZocMTag());
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
  }

  @Test
  public void test16() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.findAll();
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
    assertEquals("0", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test17() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.delete((-37L));
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ID (-37) does not exist!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test18() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    try {
      zetaObjContainer0.add((ZetaObj) null);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // Input obj cannot be null!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test19() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.setMaxSize(862);
    try {
      zetaObjContainer0.add((ZetaObj) null);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // Input obj cannot be null!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test20() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.setMaxSize((-719));
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
    assertEquals("0", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test21() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.setMaxSize(999);
    try {
      zetaObjContainer0.setMaxSize(1441);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // no message in exception (getMessage() returned null)
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test22() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    ZetaObjContainer<ZetaObj> zetaObjContainer1 = zetaObjContainer0.clone();
    zetaObjContainer1.close();
    assertTrue(zetaObjContainer1.isClosed());
  }

  @Test
  public void test23() throws Throwable {
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = null;
    try {
      zetaObjContainer0 = new ZetaObjContainer<>((Class<ZetaObj>) null);
      fail("Expecting exception: IllegalArgumentException");

    } catch (IllegalArgumentException e) {
      //
      // Input clz cannot be null!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

  @Test
  public void test24() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.getPersistenceProvider();
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
    assertEquals("0", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test25() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    JsonFactory jsonFactory0 = new JsonFactory();
    ObjectMapper objectMapper0 = new ObjectMapper(jsonFactory0);
    JacksonPersistenceProvider<ZetaObj> jacksonPersistenceProvider0 =
        new JacksonPersistenceProvider<>("ID (", objectMapper0);
    zetaObjContainer0.setPersistenceProvider(jacksonPersistenceProvider0);
    zetaObjContainer0.getPersistenceProvider();
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
    assertEquals("0", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test26() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    int int0 = zetaObjContainer0.getMaxSize();
    assertEquals("0", zetaObjContainer0.getZocMTag());
    assertEquals(Integer.MAX_VALUE, int0);
  }

  @Test
  public void test27() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.updateZocMTag();
    zetaObjContainer0.clone();
    // // Unstable assertion: assertEquals("NpInfHxyYes", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test28() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    Class<ZetaObj> class1 = zetaObjContainer0.getZetaObjType();
    assertNotNull(class1);
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
    assertEquals("0", zetaObjContainer0.getZocMTag());
  }

  @Test
  public void test29() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.isClosed();
    assertEquals("0", zetaObjContainer0.getZocMTag());
    assertEquals(2147483647, zetaObjContainer0.getMaxSize());
  }

  @Test
  public void test30() throws Throwable {
    Class<ZetaObj> class0 = ZetaObj.class;
    ZetaObjContainer<ZetaObj> zetaObjContainer0 = new ZetaObjContainer<>(class0);
    zetaObjContainer0.size();
    try {
      zetaObjContainer0.findById(719L);
      fail("Expecting exception: ZocException");

    } catch (ZocException e) {
      //
      // ID (719) does not exist!
      //
      assertThrownBy("com.trendmicro.zeta.common.zoc_new.ZetaObjContainer", e);
    }
  }

*/
}
