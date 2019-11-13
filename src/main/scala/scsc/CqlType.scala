package scsc

import java.net.InetAddress
import java.nio.ByteBuffer
import java.sql.Timestamp
import java.util.Date

sealed trait CqlType {
  type JavaType
}

object CqlType {
  type Aux[A] = CqlType {type JavaType = A}

  sealed trait ASCII extends CqlType {
    type JavaType = String
  }

  implicit object ASCII extends ASCII

  sealed trait BIGINT extends CqlType {
    type JavaType = Long
  }

  implicit object BIGINT extends BIGINT

  sealed trait BLOB extends CqlType {
    type JavaType = ByteBuffer
  }

  implicit object BLOB extends BLOB

  sealed trait BOOLEAN extends CqlType {
    type JavaType = Boolean
  }

  implicit object BOOLEAN extends BOOLEAN

  sealed trait COUNTER extends CqlType {
    type JavaType = Long
  }

  implicit object COUNTER extends COUNTER

  sealed trait DATE extends CqlType {
    type JavaType = Date
  }

  implicit object DATE extends DATE

  sealed trait DECIMAL extends CqlType {
    type JavaType = BigDecimal
  }

  implicit object DECIMAL extends DECIMAL

  sealed trait DOUBLE extends CqlType {
    type JavaType = Double
  }

  implicit object DOUBLE extends DOUBLE

  sealed trait DURATION extends CqlType

  implicit object DURATION extends DURATION

  sealed trait FLOAT extends CqlType {
    type JavaType = Float
  }

  implicit object FLOAT extends FLOAT

  sealed trait INET extends CqlType {
    type JavaType = InetAddress
  }

  implicit object INET extends INET

  sealed trait INT extends CqlType {
    type JavaType = Int
  }

  implicit object INT extends INT

  sealed trait SMALLINT extends CqlType

  implicit object SMALLINT extends SMALLINT

  sealed trait TEXT extends CqlType {
    type Javatype = String
  }

  implicit object TEXT extends TEXT

  sealed trait TIME extends CqlType {
    type JavaType = Long
  }

  implicit object TIME extends TIME

  sealed trait TIMESTAMP extends CqlType {
    type JavaType = Timestamp
  }

  implicit object TIMESTAMP extends TIMESTAMP

  sealed trait TIMEUUID extends CqlType {
    type JavaType = java.util.UUID
  }

  implicit object TIMEUUID extends TIMEUUID

  sealed trait TINYINT extends CqlType {
    type JavaType = Int
  }

  implicit object TINYINT extends TINYINT

  sealed trait UUID extends CqlType {
    type JavaType = java.util.UUID
  }

  implicit object UUID extends UUID

  sealed trait VARINT extends CqlType {
    type javaType = BigInt
  }

  implicit object VARINT extends VARINT

}