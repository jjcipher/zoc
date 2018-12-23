package jjcipher.zoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code CheckedString} can be applied to the string type fields of a ZetaObj and so the
 * {@code ZetaObjContainer} can enforce checking of the fields.
 *
 * @since ZOC 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckedString {

  String NOT_NULL = "NotNull";
  String NOT_EMPTY = "NotEmpty";
  String UNIQUE = "Unique";

  /**
   * To indicate what to be checked for the target string. Possible values are:
   * <li>CheckedString.NOT_NULL - the target string cannot be null
   * <li>CheckedString.NOT_EMPTY- the target string cannot be null nor empty.
   * <li>CheckedString.UNIQUE - the target string cannot be null not empty, and it has to be unique
   * in the ZOC.
   */
  String criteria() default "";

  /**
   * To indicate the target string has to be longer than the minimum length.
   * <p>
   * Note: the target string cannot be null when minLength is set.
   */
  int minLength() default 0;

  /**
   * To indicate the target string has to be longer than the maximum length.
   * <p>
   * Note: the target string can be null if only maxLength is set.
   */
  int maxLength() default Integer.MAX_VALUE;

  /**
   * To indicate the target string needs to be match the given pattern, if it is not empty.
   * <p>
   * Note: the pattern check will be skipped when the target string is null or empty. So to enforce
   * not-null string, one can set criteria = NOT_EMPTY.
   */
  String pattern() default "";
}
