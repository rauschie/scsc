package scsc.ops.hlist

import shapeless.{HList, ::, HNil}

sealed trait GetNames[L] {
  type Out <: HList
}

object GetNames {

  import scsc.Column

  type Aux[L, O <: HList] = GetNames[L] {
    type Out = O
  }
  implicit def getHConsNames[N <: Singleton with String, H, T <: HList](
      implicit ev: H <:< Column[N, _],
      tailNames: GetNames[T]
  ): Aux[H :: T, N :: tailNames.Out] = new GetNames[H :: T] {
    type Out = N :: tailNames.Out
  }

  implicit object getHNilName extends GetNames[HNil] {
    type Out = HNil
  }
}
