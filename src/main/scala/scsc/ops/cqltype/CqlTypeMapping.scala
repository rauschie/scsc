package scsc.ops.cqltype

import scsc.CqlDataType
import scsc.ops.UnaryTypeMapping

sealed trait CqlTypeMapping[C <: CqlDataType] extends UnaryTypeMapping[C]

object CqlTypeMapping {

  import scsc.CqlDataType._

  type Aux[C <: CqlDataType, M] = CqlTypeMapping[C] { type MappedTo = M }

  implicit object BIGINTMapping extends CqlTypeMapping[BIGINT] {
    type MappedTo = Long
  }

  implicit object BLOBMapping extends CqlTypeMapping[BLOB] {
    type MappedTo = java.nio.ByteBuffer
  }

  implicit object BOOLEANMapping extends CqlTypeMapping[BOOLEAN] {
    type MappedTo = Boolean
  }

  implicit object COUNTERMapping extends CqlTypeMapping[COUNTER] {
    type MappedTo = Long
  }

  implicit object DATEMapping extends CqlTypeMapping[DATE] {
    type MappedTo = java.util.Date
  }

  implicit object DECIMALMapping extends CqlTypeMapping[DECIMAL] {
    type MappedTo = BigDecimal
  }

  implicit object DOUBLEMapping extends CqlTypeMapping[DOUBLE] {
    type MappedTo = Double
  }

  implicit object DURATIONMapping extends CqlTypeMapping[DURATION]

  implicit object FLOATMapping extends CqlTypeMapping[FLOAT] {
    type MappedTo = Float
  }

  implicit object INETMapping extends CqlTypeMapping[INET] {
    type MappedTo = java.net.InetAddress
  }

  implicit object INTMapping extends CqlTypeMapping[INT] {
    type MappedTo = Int
  }

  implicit object SMALLINTMapping extends CqlTypeMapping[SMALLINT]

  implicit object TEXTMapping extends CqlTypeMapping[TEXT] {
    type MappedTo = String
  }

  implicit object TIMEMapping extends CqlTypeMapping[TIME] {
    type MappedTo = Long
  }

  implicit object TIMESTAMPMapping extends CqlTypeMapping[TIMESTAMP] {
    type MappedTo = java.sql.Timestamp
  }

  implicit object TIMEUUIDMapping extends CqlTypeMapping[TIMEUUID] {
    type MappedTo = java.util.UUID
  }

  implicit object TINYINTMapping extends CqlTypeMapping[TINYINT] {
    type MappedTo = Int
  }

  implicit object UUIDMapping extends CqlTypeMapping[UUID] {
    type MappedTo = java.util.UUID
  }

  implicit object VARINTMapping extends CqlTypeMapping[VARINT] {
    type MappedTo = BigInt
  }

  implicit object ASCIIMapping extends CqlTypeMapping[ASCII] {
    override type MappedTo = String
  }

}
