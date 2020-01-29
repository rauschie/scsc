package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{::, HList, HNil, NotContainsConstraint}

sealed trait SetUnion[M, N] extends BinaryTypeMapping[M, N] { type MappedTo <: HList }

object SetUnion extends LowPrioritySetUnion {

  type Aux[M, N, O <: HList] = SetUnion[M, N] {
    type MappedTo = O
  }

  implicit def add[H, T <: HList, N <: HList](
      implicit ev: NotContainsConstraint[N, H],
      union: SetUnion[T, H :: N]
  ): Aux[H :: T, N, union.MappedTo] = new SetUnion[H :: T, N] {
    type MappedTo = union.MappedTo
  }

  implicit def addHNil[L <: HList]: Aux[HNil, L, L] = new SetUnion[HNil, L] {
    type MappedTo = L
  }
}

trait LowPrioritySetUnion {

  implicit def skip[H, T <: HList, N](
      implicit union: SetUnion[T, N]
  ): SetUnion.Aux[H :: T, N, union.MappedTo] = new SetUnion[H :: T, N] {
    type MappedTo = union.MappedTo
  }
}
