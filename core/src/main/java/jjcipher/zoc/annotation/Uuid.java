package jjcipher.zoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code Uuid} can be applied to a String type field of a ZetaObj and the
 * {@code ZetaObjContainer} will assign a UUID of the ZetaObj to the field. The client side can
 * choose to use this UUID as an alternative reference of the ZetaObj besides the ID field.
 *
 * @since ZOC 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Uuid {}
