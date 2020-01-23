package scsc.ops.cqltype

trait GetCqlTypeName[C] {
  def apply(): String
}

object GetCqlTypeName {

  import scsc.CqlDataType._

  type Aux[C, M] = GetCqlTypeName[C] { type MappedTo = M }

  implicit object BIGINTtoString extends GetCqlTypeName[BIGINT] {
    def apply(): String = "BOOLEAN"
  }

  implicit object BLOBtoString extends GetCqlTypeName[BLOB] {
    def apply(): String = "BLOB"
  }

  implicit object BOOLEANtoString extends GetCqlTypeName[BOOLEAN] {
    def apply(): String = "BOOLEAN"
  }

  implicit object COUNTERtoString extends GetCqlTypeName[COUNTER] {
    def apply(): String = "COUNTER"
  }

  implicit object DATEtoString extends GetCqlTypeName[DATE] {
    def apply(): String = "DATE"
  }

  implicit object DECIMALtoString extends GetCqlTypeName[DECIMAL] {

    def apply(): String = "DECIMAL"
  }

  implicit object DOUBLEtoString extends GetCqlTypeName[DOUBLE] {

    def apply(): String = "DOUBLE"
  }

  implicit object DURATIONtoString extends GetCqlTypeName[DURATION] {
    def apply(): String = "DURATION"
  }

  implicit object FLOATtoString extends GetCqlTypeName[FLOAT] {

    def apply(): String = "FLOAT"
  }

  implicit object INETtoString extends GetCqlTypeName[INET] {

    def apply(): String = "INET"
  }

  implicit object INTtoString extends GetCqlTypeName[INT] {

    def apply(): String = "INT"
  }

  implicit object SMALLINTtoString extends GetCqlTypeName[SMALLINT] {
    def apply(): String = "SMALLINT"
  }

  implicit object TEXTtoString extends GetCqlTypeName[TEXT] {

    def apply(): String = "TEXT"
  }

  implicit object TIMEtoString extends GetCqlTypeName[TIME] {

    def apply(): String = "TIME"
  }

  implicit object TIMESTAMPtoString extends GetCqlTypeName[TIMESTAMP] {
    type MappedTo = java.sql.Timestamp

    def apply(): String = "TIMESTAMP"
  }

  implicit object TIMEUUIDtoString extends GetCqlTypeName[TIMEUUID] {

    def apply(): String = "TIMEUUID"
  }

  implicit object TINYINTtoString extends GetCqlTypeName[TINYINT] {

    def apply(): String = "TINYINT"
  }

  implicit object UUIDtoString extends GetCqlTypeName[UUID] {
    def apply(): String = "UUID"
  }

  implicit object VARINTtoString extends GetCqlTypeName[VARINT] {
    def apply(): String = "VARINT"
  }

  implicit object ASCIItoString extends GetCqlTypeName[ASCII] {
    def apply(): String = "ASCII"
  }

}
