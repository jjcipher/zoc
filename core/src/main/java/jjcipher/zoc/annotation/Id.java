package jjcipher.zoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code Id} can be applied to a Long type field of a ZetaObj and so the
 * {@code ZetaObjContainer} can reference the ZetaObj by using the value of this field as the
 * ID of the ZetaObj.
 *
 * @since ZOC 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

  String AUTO = "Auto";
  String PROVIDED = "Provided";

  /**
   * To request how the {@code ZetaObjContainer} to process this field. Possible values are:
   * <li>Id.AUTO - the system will generate an ID value for this field if the given value is
   * invalid (when it is 0, negative numbers, or conflicts with other's ID).
   * <li>Id.PROVIDED - the system will always use this provided value as the ID of the ZetaObj,
   * and it may throw exception if the provided value is invalid.
   */
  String setting() default AUTO;
}
