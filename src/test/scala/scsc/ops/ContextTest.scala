package scsc.ops

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.{TestClustering, TestOptional, TestPartitioning}
import shapeless.{HNil, the}

@RunWith(classOf[JUnitRunner])
class ContextTest extends AnyWordSpec {
  "Context" when {
    the[Context[TestPartitioning, HNil, HNil]]
    the[Context[TestPartitioning, TestClustering, HNil]]
    the[Context[TestPartitioning, HNil, TestOptional]]
    the[Context[TestPartitioning, TestClustering, TestOptional]]
    "summoned" should {
      "be available" in {
        //       the[MapColumns[Column.Aux[INT, "Foo"] :: HNil, HNil, HNil]]
      }
    }
  }
}
