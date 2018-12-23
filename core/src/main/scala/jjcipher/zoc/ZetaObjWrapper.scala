package jjcipher.zoc

@SerialVersionUID(100L)
final class ZetaObjWrapper[T <: ZetaObj] private[zoc](val id: Long, val ref: T)
  extends Serializable {
}
