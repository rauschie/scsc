package scsc

import scsc.ops.cqltype.GetCqlTypeName

sealed trait CqlType

object CqlType {

  sealed trait BIGINT extends CqlType

  sealed trait BLOB extends CqlType

  sealed trait BOOLEAN extends CqlType

  sealed trait COUNTER extends CqlType

  sealed trait DATE extends CqlType

  sealed trait DECIMAL extends CqlType

  sealed trait DOUBLE extends CqlType

  sealed trait DURATION extends CqlType

  sealed trait FLOAT extends CqlType

  sealed trait INET extends CqlType

  sealed trait INT extends CqlType

  sealed trait SMALLINT extends CqlType

  sealed trait TEXT extends CqlType

  sealed trait TIME extends CqlType

  sealed trait TIMESTAMP extends CqlType

  sealed trait TIMEUUID extends CqlType

  sealed trait TINYINT extends CqlType

  sealed trait UUID extends CqlType

  sealed trait VARINT extends CqlType

  sealed trait ASCII extends CqlType

  sealed trait CqlTypeOps[C <: CqlType] {

    def getName(implicit toString: GetCqlTypeName[C]): String = toString()

    def apply[N <: Singleton with String](name: N): Column[N, C] = Column[N, C]
  }

  implicit object BLOB extends CqlTypeOps[BLOB]

  implicit object BOOLEAN extends CqlTypeOps[BOOLEAN]

  implicit object COUNTER extends CqlTypeOps[COUNTER]

  implicit object DATE extends CqlTypeOps[DATE]

  implicit object DECIMAL extends CqlTypeOps[DECIMAL]

  implicit object DOUBLE extends CqlTypeOps[DOUBLE]

  implicit object DURATION extends CqlTypeOps[DURATION]

  implicit object FLOAT extends CqlTypeOps[FLOAT]

  implicit object INET extends CqlTypeOps[INET]

  implicit object INT extends CqlTypeOps[INT]

  implicit object SMALLINT extends CqlTypeOps[SMALLINT]

  implicit object TEXT extends CqlTypeOps[TEXT]

  implicit object TIME extends CqlTypeOps[TIME]

  implicit object TIMESTAMP extends CqlTypeOps[TIMESTAMP]

  implicit object TIMEUUID extends CqlTypeOps[TIMEUUID]

  implicit object TINYINT extends CqlTypeOps[TINYINT]

  implicit object UUID extends CqlTypeOps[UUID]

  implicit object VARINT extends CqlTypeOps[VARINT]

  implicit object ASCII extends CqlTypeOps[ASCII]

  implicit object BIGINT extends CqlTypeOps[BIGINT]

}
