package scsc.ops.crud

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CreateTest extends AnyWordSpec {
  "Create" when {
    "summoned" should {
      "have the right output" in {
        import scsc.CqlType.{BOOLEAN, DOUBLE, INT, TEXT}
        import shapeless.::
        assertResult("""CREATE TABLE a.b (
            |foo TEXT,
            |bar INT,
            |baz DOUBLE,
            |qux BOOLEAN,
            |PRIMARY KEY ((foo, bar), baz))""".stripMargin) {
          import scsc._
          import shapeless.{the, HNil}
          type TestPartitioning =
            Column.Aux[TEXT, "foo"] :: Column.Aux[INT, "bar"] :: HNil
          type TestClustering = Column.Aux[DOUBLE, "baz"] :: HNil
          type TestOptional = Column.Aux[BOOLEAN, "qux"] :: HNil
          implicitly[Create[TestPartitioning, TestClustering, TestOptional]]
            .apply("a", "b")
            .getQuery
        }
      }
    }
  }
}
