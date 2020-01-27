package scsc.ops.show

trait CqlTypeShow[C] extends NullaryShow[C] {
  def apply(): String
  def show(cqlType: C): String = apply()
}

object CqlTypeShow {

  type Aux[C, M] = CqlTypeShow[C] { type MappedTo = M }
  import scsc.CqlDataType._
  implicit object BIGINTShow extends CqlTypeShow[BIGINT] {
    def apply(): String = "BOOLEAN"

  }

  implicit object BLOBShow extends CqlTypeShow[BLOB] {
    def apply(): String = "BLOB"
  }

  implicit object BOOLEANShow extends CqlTypeShow[BOOLEAN] {
    def apply(): String = "BOOLEAN"
  }

  implicit object COUNTERShow extends CqlTypeShow[COUNTER] {
    def apply(): String = "COUNTER"
  }

  implicit object DATEShow extends CqlTypeShow[DATE] {
    def apply(): String = "DATE"
  }

  implicit object DECIMALShow extends CqlTypeShow[DECIMAL] {

    def apply(): String = "DECIMAL"
  }

  implicit object DOUBLEShow extends CqlTypeShow[DOUBLE] {

    def apply(): String = "DOUBLE"
  }

  implicit object DURATIONShow extends CqlTypeShow[DURATION] {
    def apply(): String = "DURATION"
  }

  implicit object FLOATShow extends CqlTypeShow[FLOAT] {

    def apply(): String = "FLOAT"
  }

  implicit object INETShow extends CqlTypeShow[INET] {

    def apply(): String = "INET"
  }

  implicit object INTShow extends CqlTypeShow[INT] {

    def apply(): String = "INT"
  }

  implicit object SMALLINTShow extends CqlTypeShow[SMALLINT] {
    def apply(): String = "SMALLINT"
  }

  implicit object TEXTShow extends CqlTypeShow[TEXT] {

    def apply(): String = "TEXT"
  }

  implicit object TIMEShow extends CqlTypeShow[TIME] {

    def apply(): String = "TIME"
  }

  implicit object TIMESTAMPShow extends CqlTypeShow[TIMESTAMP] {
    type MappedTo = java.sql.Timestamp

    def apply(): String = "TIMESTAMP"
  }

  implicit object TIMEUUIDShow extends CqlTypeShow[TIMEUUID] {

    def apply(): String = "TIMEUUID"
  }

  implicit object TINYINTShow extends CqlTypeShow[TINYINT] {

    def apply(): String = "TINYINT"
  }

  implicit object UUIDShow extends CqlTypeShow[UUID] {
    def apply(): String = "UUID"
  }

  implicit object VARINTShow extends CqlTypeShow[VARINT] {
    def apply(): String = "VARINT"
  }

  implicit object ASCIIShow extends CqlTypeShow[ASCII] {
    def apply(): String = "ASCII"
  }
}
