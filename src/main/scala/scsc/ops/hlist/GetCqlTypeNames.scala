package scsc.ops.hlist

import shapeless.HList

sealed trait GetTypeNames[L <: HList] {
  def apply(): List[String]
}

object GetTypeNames {

  import scsc.ops.cqltype.Name
  import scsc.{Column, CqlType}
  import scsc.CqlType.CqlTypeOps
  import shapeless.HNil

  implicit def getHConsTypeNames[A <: CqlType, H, T <: HList](implicit ev: H <:< Column[A],
                                                              getName: Name[A],
                                                              getTailTypeNames: GetTypeNames[T]): GetTypeNames[shapeless.::[H, T]] = new GetTypeNames[shapeless.::[H, T]] {
    def apply(): List[String] = getName() :: getTailTypeNames()
  }

  implicit val getHNilTypeName: GetTypeNames[HNil] = new GetTypeNames[HNil] {
    def apply(): List[String] = Nil
  }
}
