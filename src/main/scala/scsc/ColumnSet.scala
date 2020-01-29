package scsc

sealed trait ColumnSet[L] {

  import scsc.ops.hlist.SetUnion
  import shapeless.HList
  type Names <: HList
  type Values <: HList

  def union[A, O<:HList](other: ColumnSet[A])(implicit union: SetUnion.Aux[L, A, O],
                                       ev: ColumnSet[O]): ColumnSet[O] = ev
}

object ColumnSet {
//todo test this
  import scsc.ops.hlist.{GetNames, GetTypes}
  import shapeless.{HList, IsDistinctConstraint}

  type Aux[L, Nl <: HList, Vl <: HList] = ColumnSet[L] {
    type Names = Nl
    type Values = Vl
  }

  def apply[L](implicit cs: ColumnSet[L]): ColumnSet[L] = cs

  implicit def columnSet[L<:HList](implicit ev: IsDistinctConstraint[L],
                            getNames: GetNames[L],
                            getTypes: GetTypes[L]): Aux[L, getNames.MappedTo, getTypes.MappedTo] =
    new ColumnSet[L] {
      type Names = getNames.MappedTo
      type Values = getTypes.MappedTo
    }
}
