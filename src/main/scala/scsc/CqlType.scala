package scsc

import java.net.InetAddress
import java.nio.ByteBuffer
import java.sql.Timestamp
import java.util.Date

import com.datastax.oss.driver.api.core.CqlIdentifier

sealed trait CqlType {
  type JavaType
}

sealed trait RawCqlType extends CqlType

final class TaggedCqlType[A <: RawCqlType, B <: Singleton with String](nameTag: B)(implicit cqlType: A) extends CqlType {
  type JavaType = cqlType.JavaType
  type Name = B
  val cqlIdentifier: CqlIdentifier = CqlIdentifier.fromCql(nameTag)
}

object RawCqlType {
  type Aux[A] = RawCqlType {type JavaType = A}

  sealed trait ASCII extends RawCqlType {
    type JavaType = String
  }

  implicit object ASCII extends ASCII

  sealed trait BIGINT extends RawCqlType {
    type JavaType = Long
  }

  implicit object BIGINT extends BIGINT

  sealed trait BLOB extends RawCqlType {
    type JavaType = ByteBuffer
  }

  implicit object BLOB extends BLOB

  sealed trait BOOLEAN extends RawCqlType {
    type JavaType = Boolean
  }

  implicit object BOOLEAN extends BOOLEAN

  sealed trait COUNTER extends RawCqlType {
    type JavaType = Long
  }

  implicit object COUNTER extends COUNTER

  sealed trait DATE extends RawCqlType {
    type JavaType = Date
  }

  implicit object DATE extends DATE

  sealed trait DECIMAL extends RawCqlType {
    type JavaType = BigDecimal
  }

  implicit object DECIMAL extends DECIMAL

  sealed trait DOUBLE extends RawCqlType {
    type JavaType = Double
  }

  implicit object DOUBLE extends DOUBLE

  sealed trait DURATION extends RawCqlType

  implicit object DURATION extends DURATION

  sealed trait FLOAT extends RawCqlType {
    type JavaType = Float
  }

  implicit object FLOAT extends FLOAT

  sealed trait INET extends RawCqlType {
    type JavaType = InetAddress
  }

  implicit object INET extends INET

  sealed trait INT extends RawCqlType {
    type JavaType = Int
  }

  implicit object INT extends INT

  sealed trait SMALLINT extends RawCqlType

  implicit object SMALLINT extends SMALLINT

  sealed trait TEXT extends RawCqlType {
    type JavaType = String
  }

  implicit object TEXT extends TEXT

  sealed trait TIME extends RawCqlType {
    type JavaType = Long
  }

  implicit object TIME extends TIME

  sealed trait TIMESTAMP extends RawCqlType {
    type JavaType = Timestamp
  }

  implicit object TIMESTAMP extends TIMESTAMP

  sealed trait TIMEUUID extends RawCqlType {
    type JavaType = java.util.UUID
  }

  implicit object TIMEUUID extends TIMEUUID

  sealed trait TINYINT extends RawCqlType {
    type JavaType = Int
  }

  implicit object TINYINT extends TINYINT

  sealed trait UUID extends RawCqlType {
    type JavaType = java.util.UUID
  }

  implicit object UUID extends UUID

  sealed trait VARINT extends RawCqlType {
    type javaType = BigInt
  }

  implicit object VARINT extends VARINT

}