package scsc.ops.columns

import scsc.Column
import shapeless.{::, HList, HNil}

trait Underlying[L <: HList] {

  type Out <: HList
}

object Underlying extends LowPriorityUnderlying {
  type Aux[L <: HList, O] = Underlying[L] {type Out = O}

  def apply[A <: HList](implicit underlying: Underlying[A]): Underlying.Aux[A, underlying.Out] = underlying

  implicit def hConsUnderlying[H <: Column, T <: HList, U](implicit underlying: Underlying[T],
                                                           ev: H <:< Column.BackedBy[U]): Underlying.Aux[H :: T, U :: underlying.Out] = new Underlying[H :: T] {
    type Out = U :: underlying.Out
  }
}

trait LowPriorityUnderlying {
  /*TODO
  this probably isnt necessary
   */
  implicit def hNilUnderlying: Underlying[HNil] = new Underlying[HNil] {
    type Out = HNil
  }
}
