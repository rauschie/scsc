package scsc

import scsc.ops.cqltype.GetCqlTypeName

sealed trait CqlDataType

object CqlDataType {

  sealed trait BIGINT extends CqlDataType

  sealed trait BLOB extends CqlDataType

  sealed trait BOOLEAN extends CqlDataType

  sealed trait COUNTER extends CqlDataType

  sealed trait DATE extends CqlDataType

  sealed trait DECIMAL extends CqlDataType

  sealed trait DOUBLE extends CqlDataType

  sealed trait DURATION extends CqlDataType

  sealed trait FLOAT extends CqlDataType

  sealed trait INET extends CqlDataType

  sealed trait INT extends CqlDataType

  sealed trait SMALLINT extends CqlDataType

  sealed trait TEXT extends CqlDataType

  sealed trait TIME extends CqlDataType

  sealed trait TIMESTAMP extends CqlDataType

  sealed trait TIMEUUID extends CqlDataType

  sealed trait TINYINT extends CqlDataType

  sealed trait UUID extends CqlDataType

  sealed trait VARINT extends CqlDataType

  sealed trait ASCII extends CqlDataType

  sealed trait CqlTypeOps[C <: CqlDataType] {

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
