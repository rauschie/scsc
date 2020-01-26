package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetNamesTest extends AnyWordSpec {
  import shapeless.{the, HNil, ::}
  "GetNames" when {
    "called on HCons" should {
      "return the right names" in {
        import scsc.TestP0
        val gn = the[GetNames[TestP0]]
        the[gn.MappedTo <:< ("foo" :: "bar" :: HNil)]
      }
    }
    "called on HNil" should {
      "return the right names" in {
        val gn1 = the[GetNames[HNil]]
        the[gn1.MappedTo <:< HNil]
      }
    }
  }
}
