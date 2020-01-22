package scsc.ops.hlist

import shapeless.{::, HList}

trait FindByName[L, N <: String] { type Out }

object FindByName {

  import scsc.Column

  type Aux[L, N <: String, O] = FindByName[L, N] {
    type Out = O
  }

  implicit def found[N <: Singleton with String, A, T <: HList]
      : Aux[Column[N, A] :: T, N, Column[N, A]] =
    new FindByName[Column[N, A] :: T, N] {
      type Out = Column[N, A]
    }

  implicit def keepLooking[H, T <: HList, N <: Singleton with String](
      implicit findInTail: FindByName[T, N]
  ): Aux[H :: T, N, findInTail.Out] =
    new FindByName[H :: T, N] {
      type Out = findInTail.Out
    }

}
