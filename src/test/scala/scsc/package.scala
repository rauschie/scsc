package object scsc {

  import scsc.CqlDataType._
  import shapeless.{::, HNil}

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil
  type TestP0 = Column["foo", TEXT] :: Column["bar", INT] :: HNil
  type TestC0 = Column["baz", DOUBLE] :: HNil
  type TestO0 = Column["qux", BOOLEAN] :: HNil
  val partitioningTestColumns: TestP0 = TEXT("foo") :: INT("bar") :: HNil
  val clusteringTestColumns: TestC0 = DOUBLE("baz") :: HNil
  val optionalTestColumns: TestO0 = BOOLEAN("qux") :: HNil
}
