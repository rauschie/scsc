package scsc.ops.hlist

import shapeless.HList

trait ToKeyColumns[L] {
  type Out <: HList

  def apply(): Out
}

object ToKeyColumns {

  import scsc.{Column, CqlDataType}
  import scsc.ops.cqltype.CqlTypeMapping
  import shapeless.{::, HNil}

  type Aux[L0, O <: HList] = ToKeyColumns[L0] { type Out = O }

  implicit def toHConsColumns[C <: CqlDataType, N <: Singleton with String, T <: HList](
      implicit mapping: CqlTypeMapping[C],
      toTailColumns: ToKeyColumns[T]
    ): Aux[Column[N, C] :: T, Column[N, mapping.MappedTo] :: toTailColumns.Out] = new ToKeyColumns[Column[N, C] :: T] {
    type Out = Column[N, mapping.MappedTo] :: toTailColumns.Out

    def apply(): Out = Column[N, mapping.MappedTo] :: toTailColumns()
  }

  implicit object toHNilColumn extends ToKeyColumns[HNil] {
    type Out = HNil

    def apply(): Out = HNil
  }

}
