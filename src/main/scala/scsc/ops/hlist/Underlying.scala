package scsc.ops.hlist

import scsc.Column
import shapeless.{::, HList, HNil}

sealed trait Underlying[L] {
  type Out <: HList
}

object Underlying {
  type Aux[L, O0 <: HList] = Underlying[L] { type Out = O0 }

  implicit def hConsUnderlying[A, H, T <: HList](
      implicit ev: H <:< Column[A],
      extractTail: Underlying[T]
  ): Aux[H :: T, A :: extractTail.Out] = new Underlying[H :: T] {
    type Out = A :: extractTail.Out
  }

  implicit object hNilUnderlying extends Underlying[HNil] {
    type Out = HNil
  }

}
