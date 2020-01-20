package scsc.ops

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.{TestClustering, TestOptional, TestPartitioning}
import shapeless.{HNil, the}

@RunWith(classOf[JUnitRunner])
class ContextTest extends AnyWordSpec {
  "Context" when {
    val pCtx = the[Context[TestPartitioning, HNil, HNil]]
    val ptCtx = the[Context[TestPartitioning, TestClustering, HNil]]
    val poCtx = the[Context[TestPartitioning, HNil, TestOptional]]
    val pcoCtx = the[Context[TestPartitioning, TestClustering, TestOptional]]
    "summoned" should {
      "have the right types" in {
        import scsc.{TestKey, TestRecord}
        the[pcoCtx.Key<:<TestKey]
        the[pcoCtx.Record<:<TestRecord]
      }
    }
  }
}
