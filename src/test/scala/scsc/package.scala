package object scsc {

  import scsc.CqlType.{BOOLEAN, DOUBLE, INT, TEXT}
  import scsc.ops.hlist.{FindByName, GetNames, GetTypes}
  import shapeless.{::, the, HNil}
  import shapeless.ops.hlist.{Filter, Selector}

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil
  type TestPartitioning = Column["foo", TEXT] :: Column["bar", INT] :: HNil
  val v=the[FindByName[TestPartitioning,"foo"]]
  the[v.Out<:<Column["foo",TEXT]]
  val v1=the[FindByName[TestPartitioning,"bar"]]
  the[v1.Out<:<Column["bar",INT]]
  //val v2=the[FindByName[TestPartitioning,"baz"]]

  type TestClustering = Column["baz", DOUBLE] :: HNil
  type TestOptional = Column["qux", BOOLEAN] :: HNil
  val partitioningTestColumns: TestPartitioning = TEXT("foo") :: INT("bar") :: HNil
  val clusteringTestColumns: TestClustering = DOUBLE("baz") :: HNil
  val optionalTestColumns: TestOptional = BOOLEAN("qux") :: HNil
  the[GetTypes[TestPartitioning]]
  the[GetNames[TestPartitioning]]
}
