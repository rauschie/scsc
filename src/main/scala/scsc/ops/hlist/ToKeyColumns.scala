package scsc.ops.hlist

import shapeless.HList

trait ToKeyColumns[L] {
  type Out<:HList

  def apply(): Out
}

object ToKeyColumns {

  import scsc.{Column, CqlType}
  import scsc.ops.cqltype.CqlTypeMapping
  import shapeless.{::, HNil}

  type Aux[L0, O<:HList] = ToKeyColumns[L0] {type Out = O}

  implicit def toHConsColumns[C <: CqlType,
    N <: Singleton with String,
    T <: HList](implicit mapping: CqlTypeMapping[C],
                name: ValueOf[N],
                toTailColumns: ToKeyColumns[T]): Aux[Column.Aux[C, N] :: T, Column.Aux[mapping.MappedTo, N] :: toTailColumns.Out] = new ToKeyColumns[Column.Aux[C, N] :: T] {
    type Out = Column.Aux[mapping.MappedTo, N] :: toTailColumns.Out

    def apply(): Out = Column[mapping.MappedTo, N] :: toTailColumns()
  }

  implicit object toHNilColumn extends ToKeyColumns[HNil] {
    type Out = HNil

    def apply(): Out = HNil
  }

}