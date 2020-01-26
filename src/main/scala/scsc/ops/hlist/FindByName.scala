package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{::, HList}

sealed trait FindByName[L, N <: String] extends BinaryTypeMapping[L, N]

object FindByName extends LowPriorityFindByName {

  import cats.Show
  import scsc.Column

  type Aux[L, N <: String, M] = FindByName[L, N] {
    type MappedTo = M
  }

  implicit def found[N <: Singleton with String, A, T <: HList]
      : Aux[Column[N, A] :: T, N, Column[N, A]] =
    new FindByName[Column[N, A] :: T, N] {
      type MappedTo = Column[N, A]
    }
}

sealed trait LowPriorityFindByName {

  import scsc.ops.hlist.FindByName.Aux

  implicit def keepLooking[H, T <: HList, N <: Singleton with String](
      implicit findInTail: FindByName[T, N]
  ): Aux[H :: T, N, findInTail.MappedTo] =
    new FindByName[H :: T, N] {
      type MappedTo = findInTail.MappedTo
    }
}
