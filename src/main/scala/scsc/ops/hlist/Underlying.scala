package scsc.ops.hlist

import scsc.Column
import shapeless.{::, HList, HNil}

sealed trait ExtractUnderlying[L] {
  type Out <: HList
}

object ExtractUnderlying {
  type Aux[L, O0 <: HList] = ExtractUnderlying[L] {type Out = O0}

  implicit def hConsUnderlying[A, H, T <: HList](implicit ev: H <:< Column[A],
                                                 extractTail: ExtractUnderlying[T]): Aux[H :: T, A :: extractTail.Out] = new ExtractUnderlying[H :: T] {
    type Out = A :: extractTail.Out
  }

  implicit object hNilExtractUnderlying$ extends ExtractUnderlying[HNil] {
    type Out = HNil
  }

}