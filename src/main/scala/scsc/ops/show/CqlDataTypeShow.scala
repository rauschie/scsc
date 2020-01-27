package scsc.ops.show

trait CqlDataTypeShow[C] extends NullaryShow[C] {
  def apply(): String
  def show(cqlType: C): String = apply()
}

object CqlDataTypeShow {

  type Aux[C, M] = CqlDataTypeShow[C] { type MappedTo = M }
  import scsc.CqlDataType._
  implicit object BIGINTShow extends CqlDataTypeShow[BIGINT] {
    def apply(): String = "BOOLEAN"

  }

  implicit object BLOBShow extends CqlDataTypeShow[BLOB] {
    def apply(): String = "BLOB"
  }

  implicit object BOOLEANShow extends CqlDataTypeShow[BOOLEAN] {
    def apply(): String = "BOOLEAN"
  }

  implicit object COUNTERShow extends CqlDataTypeShow[COUNTER] {
    def apply(): String = "COUNTER"
  }

  implicit object DATEShow extends CqlDataTypeShow[DATE] {
    def apply(): String = "DATE"
  }

  implicit object DECIMALShow extends CqlDataTypeShow[DECIMAL] {

    def apply(): String = "DECIMAL"
  }

  implicit object DOUBLEShow extends CqlDataTypeShow[DOUBLE] {

    def apply(): String = "DOUBLE"
  }

  implicit object DURATIONShow extends CqlDataTypeShow[DURATION] {
    def apply(): String = "DURATION"
  }

  implicit object FLOATShow extends CqlDataTypeShow[FLOAT] {

    def apply(): String = "FLOAT"
  }

  implicit object INETShow extends CqlDataTypeShow[INET] {

    def apply(): String = "INET"
  }

  implicit object INTShow extends CqlDataTypeShow[INT] {

    def apply(): String = "INT"
  }

  implicit object SMALLINTShow extends CqlDataTypeShow[SMALLINT] {
    def apply(): String = "SMALLINT"
  }

  implicit object TEXTShow extends CqlDataTypeShow[TEXT] {

    def apply(): String = "TEXT"
  }

  implicit object TIMEShow extends CqlDataTypeShow[TIME] {

    def apply(): String = "TIME"
  }

  implicit object TIMESTAMPShow extends CqlDataTypeShow[TIMESTAMP] {
    type MappedTo = java.sql.Timestamp

    def apply(): String = "TIMESTAMP"
  }

  implicit object TIMEUUIDShow extends CqlDataTypeShow[TIMEUUID] {

    def apply(): String = "TIMEUUID"
  }

  implicit object TINYINTShow extends CqlDataTypeShow[TINYINT] {

    def apply(): String = "TINYINT"
  }

  implicit object UUIDShow extends CqlDataTypeShow[UUID] {
    def apply(): String = "UUID"
  }

  implicit object VARINTShow extends CqlDataTypeShow[VARINT] {
    def apply(): String = "VARINT"
  }

  implicit object ASCIIShow extends CqlDataTypeShow[ASCII] {
    def apply(): String = "ASCII"
  }
}
