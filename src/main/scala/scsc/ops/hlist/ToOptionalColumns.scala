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
      toTailColumns: ToOptionalColumns[T]
  ): Aux[Column[N, C] :: T, Column[N, Option[mapping.MappedTo]] :: toTailColumns.Out] =
    new ToOptionalColumns[Column[N, C] :: T] {
      type Out = Column[N, Option[mapping.MappedTo]] :: toTailColumns.Out

      def apply(): Out = Column[N, Option[mapping.MappedTo]] :: toTailColumns()
    }

  implicit object toHNilColumn extends ToOptionalColumns[HNil] {
    type Out = HNil

    def apply(): Out = HNil
  }

}
