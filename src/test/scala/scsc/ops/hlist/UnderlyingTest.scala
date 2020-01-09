package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.Column
import shapeless.{::, HNil, the}

@RunWith(classOf[JUnitRunner])
class UnderlyingTest extends AnyWordSpec {

  "ExtractUnderlying" when {
    val extractHNil = the[Underlying[HNil]]
    val extractHCons = the[Underlying[Column.Aux[String, "foo"] :: Column.Aux[Option[Int], "bar"] :: HNil]]
    "summoned" must {
      "have the right Out type" in {
        the[extractHNil.Out <:< HNil]
        the[extractHCons.Out <:< (String :: Option[Int] :: HNil)]
      }
    }
  }
}
