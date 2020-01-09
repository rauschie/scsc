

package object scsc {

  import scsc.CqlType.{BOOLEAN, DOUBLE, INT, TEXT}
  import scsc.ops.hlist.Underlying
  import shapeless.{::, the, HNil}

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil
  type TestPartitioning = Column.Aux[TEXT, "foo"] :: Column.Aux[INT, "bar"] :: HNil
  type TestClustering = Column.Aux[DOUBLE, "baz"] :: HNil
  type TestOptional = Column.Aux[BOOLEAN, "qux"] :: HNil
  val partitioningTestColumns: TestPartitioning = TEXT["foo"] :: INT["bar"] :: HNil
  val clusteringTestColumns: TestClustering = DOUBLE["baz"] :: HNil
  val optionalTestColumns: TestOptional = BOOLEAN["qux"] :: HNil
  the[Underlying[TestPartitioning]]



}
