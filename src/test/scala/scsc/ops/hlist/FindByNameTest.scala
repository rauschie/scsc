package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FindByNameTest extends AnyWordSpec {
  import scsc.Column
  import scsc.CqlDataType._
  import shapeless.{the, HNil, ::}
  "FindByName" when {
    "given a findable name" must {
      val f = the[FindByName[Column["foo", INT] :: Column["bar", TEXT] :: HNil, "foo"]]
      "return the right type" in {
        the[f.MappedTo <:< Column["foo", INT]]
      }
    }
    "given multiple matching names" must {
      "return the first occurance" in {
        val f1 = the[FindByName[Column["foo", INT] :: Column["foo", TEXT] :: HNil, "foo"]]
        the[f1.MappedTo <:< Column["foo", INT]]
      }
    }
    "given no match" must {
      "not compile" in assertTypeError {
        "the[FindByName[Column[\"foo\", INT] :: Column[\"bar\", TEXT] :: HNil, \"baz\"]]"
      }
    }
  }
}
