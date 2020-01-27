package scsc.ops.hlist

import scsc.ops.UnaryTypeMapping
import shapeless.HList

trait ToKeyColumns[L] extends UnaryTypeMapping[L] {
  type MappedTo <: HList

}

object ToKeyColumns {

  import scsc.{Column, CqlDataType}
  import scsc.ops.cqltype.CqlTypeMapping
  import shapeless.{::, HNil}

  type Aux[L0, O <: HList] = ToKeyColumns[L0] { type MappedTo = O }

  implicit def toHConsColumns[C <: CqlDataType, N <: Singleton with String, T <: HList](
      implicit mapping: CqlTypeMapping[C],
      toTailColumns: ToKeyColumns[T]
  ): Aux[Column[N, C] :: T, Column[N, mapping.MappedTo] :: toTailColumns.MappedTo] =
    new ToKeyColumns[Column[N, C] :: T] {
      type MappedTo = Column[N, mapping.MappedTo] :: toTailColumns.MappedTo
    }

  implicit object toHNilColumn extends ToKeyColumns[HNil] {
    type MappedTo = HNil
  }
}
