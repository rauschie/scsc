package scsc.ops.hlist

import scsc.ops.UnaryTypeMapping
import shapeless.HList

trait ToOptionalColumns[L] extends UnaryTypeMapping[L] {
  type MappedTo <: HList
}

object ToOptionalColumns {

  import scsc.{Column, CqlDataType}
  import scsc.ops.cqltype.CqlTypeMapping
  import shapeless.{::, HNil}

  type Aux[L0, O <: HList] = ToOptionalColumns[L0] { type MappedTo = O }

  implicit def toHConsColumns[A <: CqlDataType, N <: Singleton with String, T <: HList](
      implicit mapping: CqlTypeMapping[A],
      toTailColumns: ToOptionalColumns[T]
  ): Aux[Column[N, A] :: T, Column[N, Option[mapping.MappedTo]] :: toTailColumns.MappedTo] =
    new ToOptionalColumns[Column[N, A] :: T] {
      type MappedTo = Column[N, Option[mapping.MappedTo]] :: toTailColumns.MappedTo
    }

  implicit object toHNilColumn extends ToOptionalColumns[HNil] {
    type MappedTo = HNil
  }
}
