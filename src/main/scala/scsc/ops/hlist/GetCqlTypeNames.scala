package scsc.ops.hlist

import shapeless.HList

sealed trait GetCqlTypeNames[L] {
  def apply(): List[String]
}

object GetCqlTypeNames {

  import scsc.ops.cqltype.GetCqlTypeName
  import scsc.Column
  import shapeless.HNil

  implicit def getHConsTypeNames[A, H, T <: HList](
      implicit ev: H <:< Column[_,A],
      getName: GetCqlTypeName[A],
      getTailNames: GetCqlTypeNames[T]
  ): GetCqlTypeNames[shapeless.::[H, T]] =
    new GetCqlTypeNames[shapeless.::[H, T]] {
      def apply(): List[String] = getName() :: getTailNames()
    }

  implicit object getHNilTypeName extends GetCqlTypeNames[HNil] {
    def apply(): List[String] = Nil
  }
}
