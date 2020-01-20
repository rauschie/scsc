package scsc.ops.hlist

import shapeless.{HList, ::, HNil}

trait ColumnSet[L] {
  type Names <: HList
  type Types <: HList
}

object ColumnSet extends {

  import scsc.Column
  import shapeless.NotContainsConstraint

  type Aux[L, N <: HList, T <: HList] = ColumnSet[L] {
    type Names = N
    type Types = T
  }

  implicit def nonEmptyColumnSet[A, N <: Singleton with String, T <: HList](
      implicit ev: NotContainsConstraint[T, Column[N, A]],
      getNames: GetNames[T],
      getTypes: GetTypes[T],
      tail: ColumnSet[T]
  ): Aux[Column[N, A] :: T, N :: getNames.Out, A :: getTypes.Out] =
    new ColumnSet[Column[N, A] :: T] {
      type Names = N :: getNames.Out
      type Types = A :: getTypes.Out
    }

  implicit object emptyColumnSet extends ColumnSet[HNil] {
    type Names = HNil
    type Types = HNil
  }
}
