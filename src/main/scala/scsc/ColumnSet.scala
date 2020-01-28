package scsc

import cats.Monoid

sealed trait ColumnSet[L]{

  import shapeless.HList
  type Names <: HList
  type Values <: HList
  //todo impl
  def union[A](other:ColumnSet[A]) = ???
}

object ColumnSet{
//todo test this
  import scsc.ops.hlist.{GetNames, GetTypes}
  import shapeless.{HList, IsDistinctConstraint}

  type Aux[L, Nl <: HList, Vl <: HList] = ColumnSet[L] {
    type Names = Nl
    type Values = Vl
  }

  def apply[L](implicit cs: ColumnSet[L]): ColumnSet[L] = cs

  implicit def columnSet[L](implicit ev: IsDistinctConstraint[L],
                            getNames: GetNames[L],
                            getTypes: GetTypes[L]): Aux[L, getNames.MappedTo, getTypes.MappedTo] =
    new ColumnSet[L] {
      type Names = getNames.MappedTo
      type Values = getTypes.MappedTo
    }
}
