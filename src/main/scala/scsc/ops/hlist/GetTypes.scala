package scsc.ops.hlist

import scsc.Column
import shapeless.{::, HList, HNil}

sealed trait GetTypes[L] {
  type Out <: HList
}

object GetTypes {
  type Aux[L, O0 <: HList] = GetTypes[L] { type Out = O0 }

  implicit def hConsGetTypes[A, H, T <: HList](
      implicit ev: H <:< Column[_, A],
      extractTail: GetTypes[T]
  ): Aux[H :: T, A :: extractTail.Out] = new GetTypes[H :: T] {
    type Out = A :: extractTail.Out
  }

  implicit object hNilGetTypes extends GetTypes[HNil] {
    type Out = HNil
  }
}
