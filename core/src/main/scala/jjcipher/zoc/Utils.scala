package jjcipher.zoc

import java.security.SecureRandom

object Utils {

  val MTAG_LENGTH = 8

  private val random = new SecureRandom()

  private val enc = java.util.Base64.getUrlEncoder.withoutPadding()

  /**
    * Generates MTag string
    * @return a random MTag
    */
  def genMTag(): String = enc.encodeToString(genRandomBytes(MTAG_LENGTH))

  /**
    * Generates a cryptographically secure random byte array.
    *
    * @param length the length of the byte array.
    * @return a random byte array.
    */
  def genRandomBytes(length: Int): Array[Byte] = {
    val bytes = new Array[Byte](length)
    random.nextBytes(bytes)
    bytes
  }

  /**
    * Generates a UUID string
    * @return a UUID string
    */
  def genUUID(): String = java.util.UUID.randomUUID.toString()
}
