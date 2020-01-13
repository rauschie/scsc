package scsc.ops.hlist

import shapeless.HList

trait ToOptionalColumns[L] {
  type Out <: HList

  def apply(): Out
}

object ToOptionalColumns {

  import scsc.{Column, CqlType}
  import scsc.ops.cqltype.CqlTypeMapping
  import shapeless.{::, HNil}

  type Aux[L0, O <: HList] = ToOptionalColumns[L0] { type Out = O }

  implicit def toHConsColumns[C <: CqlType, N <: Singleton with String, T <: HList](
      implicit mapping: CqlTypeMapping[C],
      name: ValueOf[N],
      toTailColumns: ToOptionalColumns[T]
  ): Aux[Column.Aux[C, N] :: T, Column.Aux[Option[mapping.MappedTo], N] :: toTailColumns.Out] =
    new ToOptionalColumns[Column.Aux[C, N] :: T] {
      type Out = Column.Aux[Option[mapping.MappedTo], N] :: toTailColumns.Out

      def apply(): Out = Column[Option[mapping.MappedTo], N] :: toTailColumns()
    }

  implicit object toHNilColumn extends ToOptionalColumns[HNil] {
    type Out = HNil

    def apply(): Out = HNil
  }

}
