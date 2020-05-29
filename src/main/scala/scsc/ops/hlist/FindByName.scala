package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{::, HList}

sealed trait FindByName[Columns, Name <: String]
    extends BinaryTypeMapping[Columns, Name]

object FindByName extends LowPriorityFindByName {

  import scsc.Column

  type Aux[Columns, Name <: String, M] = FindByName[Columns, Name] {
    type MappedTo = M
  }

  implicit def findByName[N <: Singleton with String, A, T <: HList]
      : Aux[Column[N, A] :: T, N, Column[N, A]] =
    new FindByName[Column[N, A] :: T, N] {
      type MappedTo = Column[N, A]
    }
}

sealed trait LowPriorityFindByName {

  import scsc.ops.hlist.FindByName.Aux

  implicit def recurse[H, T <: HList, N <: Singleton with String](
      implicit findInTail: FindByName[T, N]
  ): Aux[H :: T, N, findInTail.MappedTo] =
    new FindByName[H :: T, N] {
      type MappedTo = findInTail.MappedTo
    }
}
