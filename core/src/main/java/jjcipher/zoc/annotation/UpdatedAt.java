package jjcipher.zoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation {@code CreatedAt} can be applied to a Long type field of a ZetaObj and the
 * {@code ZetaObjContainer} will inject a UNIX timestamp (in seconds) when the ZetaObj was
 * last modified in the {@code ZetaObjContainer}.
 *
 * @since ZOC 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UpdatedAt {}
