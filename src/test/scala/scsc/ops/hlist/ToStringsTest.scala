package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.wordspec.AnyWordSpec

@RunWith(classOf[JUnitRunner])
class ToStringsTest extends AnyWordSpec {
  import shapeless.the
  "ToStrings" when {
    "called on a HList of singleton Strings" should {
      "print correctly" in {
        import scsc.TestColumnNames
        val columnNamesToStrings = the[ToStrings[TestColumnNames]]
        assertResult(columnNamesToStrings())("foo"::"bar"::"baz"::"qux"::Nil)
      }
    }
    "called on a HList of CqlDataTypes" should {
      "print correctly" in {
        import scsc.CqlDataType._
        import shapeless.{HNil, ::}
        val cqlDataTypesToStrings=the[ToStrings[INT :: TEXT :: BOOLEAN :: HNil]]
        assertResult(cqlDataTypesToStrings())("INT"::"TEXT"::"BOOLEAN"::Nil)
      }
    }
  }
}
