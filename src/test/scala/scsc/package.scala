package object scsc {

  import scsc.CqlType.{BOOLEAN, DOUBLE, INT, TEXT}
  import scsc.ops.hlist.{GetNames, GetTypes}
  import shapeless.{::, the, HNil}
  import shapeless.ops.hlist.Filter

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil
  type TestPartitioning = Column["foo", TEXT] :: Column["bar", INT] :: HNil
  val f=the[Filter[TestPartitioning, Column["foo",_]]]

  type TestClustering = Column["baz", DOUBLE] :: HNil
  type TestOptional = Column["qux", BOOLEAN] :: HNil
  val partitioningTestColumns: TestPartitioning = TEXT("foo") :: INT("bar") :: HNil
  val clusteringTestColumns: TestClustering = DOUBLE("baz") :: HNil
  val optionalTestColumns: TestOptional = BOOLEAN("qux") :: HNil
  the[GetTypes[TestPartitioning]]
  the[GetNames[TestPartitioning]]
}
