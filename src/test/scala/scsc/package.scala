package object scsc {

  import scsc.CqlDataType._
  import scsc.ops.hlist.ToOptionalColumns
  import shapeless.{::, the, HNil}

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil
  type TestP0 = Column["foo", TEXT] :: Column["bar", INT] :: HNil
  type TestC0 = Column["baz", DOUBLE] :: HNil
  type TestO0 = Column["qux", BOOLEAN] :: HNil
  type TestKeyNames = "foo" :: "bar" :: "baz" :: HNil
  type TestColumnNames = "foo" :: "bar" :: "baz" :: "qux" :: HNil
  type TestKeyColumns = Column["foo", String] :: Column["bar", Int] :: Column["baz", Double] :: HNil
  val a: Column["foo", INT] :: Column["bar", ASCII] :: Column["baz", UUID] :: HNil = INT("foo") :: ASCII("bar") :: UUID("baz") :: HNil

  type TestColumns =
    Column["foo", String] :: Column["bar", Int] :: Column["baz", Double] :: Column["qux", Option[
      Boolean
    ]] :: HNil
  val partitioningTestColumns: TestP0 = TEXT("foo") :: INT("bar") :: HNil
  val clusteringTestColumns: TestC0 = DOUBLE("baz") :: HNil
  val optionalTestColumns: TestO0 = BOOLEAN("qux") :: HNil
}
