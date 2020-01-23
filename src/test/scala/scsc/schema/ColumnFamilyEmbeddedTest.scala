package scsc.schema

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.EmbeddedCassandraTestSuite

@RunWith(classOf[JUnitRunner])
class ColumnFamilyEmbeddedTest extends AnyWordSpec with EmbeddedCassandraTestSuite {

  import scsc.CqlDataType._
  import shapeless.{::, HNil}

  type TestKey = String :: Int :: Double :: HNil
  type TestRecord = String :: Int :: Double :: Option[Boolean] :: HNil

  "ColumnFamily" when {

    val foo = ColumnFamily.createFromPartitioningColumns("foo")(TEXT("foo") :: INT("bar") :: HNil)
    val bar = ColumnFamily.createFromOptionalColumns("bar")(TEXT("foo") :: INT("bar") :: HNil,
                                                            BOOLEAN("qux") :: HNil)
    val baz = ColumnFamily.createFromKeyColumns("baz")(TEXT("foo") :: INT("bar") :: HNil,
                                                       DOUBLE("baz") :: HNil)
    val qux = ColumnFamily.create("qux")(TEXT("foo") :: INT("bar") :: HNil, DOUBLE("baz") :: HNil)(
      BOOLEAN("qux") :: HNil
    )

    "instantiated" must {
      "have the right types" in {

        import shapeless.the

        the[foo.Key <:< (String :: Int :: HNil)]
        the[bar.Key <:< (String :: Int :: HNil)]
        the[bar.Record <:< (String :: Int :: Option[Boolean] :: HNil)]
        the[baz.Key <:< (String :: Int :: Double :: HNil)]
        the[baz.Record <:< (String :: Int :: Double :: HNil)]
        the[qux.Key <:< TestKey]
        the[qux.Record <:< TestRecord]
      }

      "be able to create itself in a keyspace" in withKeySpace("test") { implicit session =>
      }
    }
  }
}
