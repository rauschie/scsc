package scsc.ops

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.{TestC0, TestP0}
import shapeless.the

@RunWith(classOf[JUnitRunner])
class ContextTest extends AnyWordSpec {
  "Context" when {
    import scsc.ops.hlist.{GetNames, GetTypes, ToKeyColumns}
    import scsc.TestO0
    import shapeless.HNil

    the[ToKeyColumns[TestP0]]
    the[ToKeyColumns[TestC0]]
    the[GetTypes[TestP0]]
    the[GetTypes[TestC0]]
    the[GetNames[TestP0]]
    the[GetNames[TestC0]]
    val pCtx = the[Context[TestP0, HNil, HNil]]
    val ptCtx = the[Context[TestP0, TestC0, HNil]]
    val poCtx = the[Context[TestP0, HNil, TestO0]]
    val pcoCtx = the[Context[TestP0, TestC0, TestO0]]
    "summoned" should {
      "have the right types" in {
      }
    }
  }
}
