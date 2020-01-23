package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import shapeless.{::, the, HNil}

@RunWith(classOf[JUnitRunner])
class GetTypesTest extends AnyWordSpec {

  "ExtractUnderlying" when {
    import scsc.Column
    val extractHNil = the[GetTypes[HNil]]
    val extractHCons = the[GetTypes[Column["foo", String] :: Column["bar", Option[Int]] :: HNil]]
    "summoned" must {
      "have the right MappedTo type" in {
        the[extractHNil.MappedTo <:< HNil]
        the[extractHCons.MappedTo <:< (String :: Option[Int] :: HNil)]
      }
    }
  }
}
