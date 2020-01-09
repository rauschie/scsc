package scsc.ops.row

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import shapeless.the


@RunWith(classOf[JUnitRunner])
class GetTest extends AnyWordSpec {
  "a Get" when {
    "implemented" must {
      "be implicitly available" in {
        the[Get[Double]]
        the[Get[String]]
      }
    }
  }
}
