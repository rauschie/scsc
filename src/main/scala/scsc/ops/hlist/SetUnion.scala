package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{::, HList, HNil, NotContainsConstraint}

sealed trait SetUnion[M, N] extends BinaryTypeMapping[M, N]{type MappedTo<:HList}

object SetUnion extends LowPrioritySetUnion {

  type Aux[M, N, O <: HList] = SetUnion[M, N] {
    type MappedTo = O
  }

  implicit def add[M <: HList, H, T <: HList](
      implicit ev: NotContainsConstraint[M, H],
      union: SetUnion[H :: M, T]
  ): Aux[M, H :: T, union.MappedTo] = new SetUnion[M, H :: T] {
    type MappedTo = union.MappedTo

  }

  implicit def addHNil[L<:HList]: Aux[L, HNil, L] = new SetUnion[L, HNil] {
    type MappedTo = L
  }
}

trait LowPrioritySetUnion {

  implicit def skip[M, H, T <: HList](
      implicit union: SetUnion[M, T]
  ): SetUnion.Aux[M, H :: T, union.MappedTo] = new SetUnion[M, H :: T] {
    type MappedTo = union.MappedTo
  }
}
