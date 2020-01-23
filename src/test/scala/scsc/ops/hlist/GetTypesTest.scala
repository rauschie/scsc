package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import shapeless.{::, the, HNil}

@RunWith(classOf[JUnitRunner])
class GetTypesTest extends AnyWordSpec {

  import scsc.Column
  "ExtractUnderlying" when {
    "given HNil" must {
      "map to the tight type" in {
        val extractHNil = the[GetTypes[HNil]]
        the[extractHNil.MappedTo <:< HNil]
      }
    }
    "given HCons" must {
      "map to the right type" in {
        val extractHCons =
          the[GetTypes[Column["foo", String] :: Column["bar", Option[Int]] :: HNil]]
        the[extractHCons.MappedTo <:< (String :: Option[Int] :: HNil)]
      }
    }
  }
}
