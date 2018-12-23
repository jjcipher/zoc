package jjcipher.zoc

case class ZocException private[zoc] (errorId: Int,
                                      message: String = "",
                                      throwable: Throwable = None.orNull)
  extends Exception(message, throwable) {

  def this(message: String, throwable: Throwable) = this(ZocException.ERR_UNDEFINED,
    message, throwable)

  def this(message: String) = this(ZocException.ERR_UNDEFINED, message)
}

object ZocException {

  val ERR_OBJ_ID_INVALID = 101

  val ERR_OBJ_ID_INCONSISTENT = 102

  val ERR_OBJ_UUID_INCONSISTENT = 103

  val ERR_OBJ_ID_NOT_EXIST = 104

  val ERR_OBJ_UUID_NOT_EXIST = 105

  val ERR_INPUT_PRIORITY_INVALID = 106

  val ERR_EXCEED_MAX_SIZE = 107

  val ERR_MTAG_MISMATCH = 110

  // The error ID for CheckedString.NOT_NULL validation error,
  // Note: thrown if the field is null.
  val ERR_CHECKED_STRING_NOT_NULL = 120

  // The error ID for CheckedString.NOT_EMPTY validation error,
  // Note: thrown if the field is null or empty.
  val ERR_CHECKED_STRING_NOT_EMPTY = 121

  // The error ID for CheckedString.UNIQUE validation error,
  // Note: thrown if the field is null or empty, or if the field is not unique.
  val ERR_CHECKED_STRING_UNIQUE = 122

  // The error ID for CheckedString.minLength() validation error,
  // Note: thrown if the field is null or the length is shorter than the required minLength.
  val ERR_CHECKED_STRING_MIN_LENGTH = 123

  // The error ID for CheckedString.maxLength() validation error,
  // Note: thrown if the field is longer than the restricted maxLength.
  val ERR_CHECKED_STRING_MAX_LENGTH = 124

  // The error ID for CheckedString.pattern() validation error,
  // Note: thrown if the field does not match to the required pattern.
  val ERR_CHECKED_STRING_PATTERN = 125

  // Runtime Server Errors
  val ERR_WITH_UNIQUE_NAME_NOT_SUPPORTED = 201

  val ERR_WITH_PRIORITY_NOT_SUPPORTED = 202

  val ERR_MULTIPLE_ID_FIELDS = 203

  // Transient Errors
  val ERR_ZOC_CLOSED = 301

  val ERR_ZOC_TYPE_MISMATCH = 302
  val ERR_ZOC_PERSIST_FAILED = 303
  val ERR_ZOC_RESTORE_FAILED = 304
  val ERR_CLONE_OBJ_FAILED = 305

  /*
   * An error ID for errors that should have not happened at production. (must be a design/coding
   * problem)
   */
  val ERR_SHOULD_NOT_HAPPEN = 999

  val ERR_UNDEFINED = -1
}

