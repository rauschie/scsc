package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{::, HList}

trait FindByName[L, N <: String] extends BinaryTypeMapping[L, N]

object FindByName {

  import scsc.Column

  type Aux[L, N <: String, O] = FindByName[L, N] {
    type MappedTo = O
  }

  implicit def found[N <: Singleton with String, A, T <: HList]
      : Aux[Column[N, A] :: T, N, Column[N, A]] =
    new FindByName[Column[N, A] :: T, N] {
      type MappedTo = Column[N, A]
    }

  implicit def keepLooking[H, T <: HList, N <: Singleton with String](
      implicit findInTail: FindByName[T, N]
  ): Aux[H :: T, N, findInTail.MappedTo] =
    new FindByName[H :: T, N] {
      type MappedTo = findInTail.MappedTo
    }

}
