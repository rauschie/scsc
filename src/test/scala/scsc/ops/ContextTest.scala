package scsc.ops

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import shapeless.{HNil, the}
import scsc.{TestP0, TestC0, TestO0}

@RunWith(classOf[JUnitRunner])
class ContextTest extends AnyWordSpec {
  "Context" when {
    val pCtx = the[Context[TestP0, HNil, HNil]]
    val ptCtx = the[Context[TestP0, TestC0, HNil]]
    val poCtx = the[Context[TestP0, HNil, TestO0]]
    val pcoCtx = the[Context[TestP0, TestC0, TestO0]]
    "summoned" should {
      "have the right types" in {
        import scsc.{TestKey, TestRecord}
        the[pcoCtx.Key <:< TestKey]
        the[pcoCtx.Record <:< TestRecord]
      }
    }
  }
}
