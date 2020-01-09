package scsc.ops.cqltype

import scsc.CqlType

trait OptionalCqlTypeMapping[C <: CqlType] {
  type MappedTo
}

object OptionalCqlTypeMapping {
  type Aux[C <: CqlType, M] = OptionalCqlTypeMapping[C] {type MappedTo = M}

  sealed trait OptionalOptionalCqlTypeMapping[C <: CqlType] extends OptionalCqlTypeMapping[C]
}
