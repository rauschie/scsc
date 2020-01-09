package scsc.ops.cqltype

import scsc.CqlType

trait Name[C <: CqlType] {
  def apply(): String
}

object Name {

  import scsc.CqlType._

  type Aux[C <: CqlType, M] = Name[C] {type MappedTo = M}


  implicit object BIGINTtoString extends Name[BIGINT] {
    def apply(): String = "BOOLEAN"
  }

  implicit object BLOBtoString extends Name[BLOB] {
    def apply(): String = "BLOB"
  }

  implicit object BOOLEANtoString extends Name[BOOLEAN] {

    def apply(): String = "BOOLEAN"
  }

  implicit object COUNTERtoString extends Name[COUNTER] {

    def apply(): String = "COUNTER"
  }

  implicit object DATEtoString extends Name[DATE] {

    def apply(): String = "DATE"
  }

  implicit object DECIMALtoString extends Name[DECIMAL] {

    def apply(): String = "DECIMAL"
  }

  implicit object DOUBLEtoString extends Name[DOUBLE] {

    def apply(): String = "DOUBLE"
  }

  implicit object DURATIONtoString extends Name[DURATION] {
    def apply(): String = "DURATION"
  }

  implicit object FLOATtoString extends Name[FLOAT] {

    def apply(): String = "FLOAT"
  }

  implicit object INETtoString extends Name[INET] {

    def apply(): String = "INET"
  }

  implicit object INTtoString extends Name[INT] {

    def apply(): String = "INT"
  }

  implicit object SMALLINTtoString extends Name[SMALLINT] {
    def apply(): String = "SMALLINT"
  }

  implicit object TEXTtoString extends Name[TEXT] {

    def apply(): String = "TEXT"
  }

  implicit object TIMEtoString extends Name[TIME] {

    def apply(): String = "TIME"
  }

  implicit object TIMESTAMPtoString extends Name[TIMESTAMP] {
    type MappedTo = java.sql.Timestamp

    def apply(): String = "TIMESTAMP"
  }

  implicit object TIMEUUIDtoString extends Name[TIMEUUID] {

    def apply(): String = "TIMEUUID"
  }

  implicit object TINYINTtoString extends Name[TINYINT] {

    def apply(): String = "TINYINT"
  }

  implicit object UUIDtoString extends Name[UUID] {

    def apply(): String = "UUID"
  }

  implicit object VARINTtoString extends Name[VARINT] {

    def apply(): String = "VARINT"
  }

  implicit object ASCIItoString extends Name[ASCII] {

    def apply(): String = "ASCII"
  }

}
