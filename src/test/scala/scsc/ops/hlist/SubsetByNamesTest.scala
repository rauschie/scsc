package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SubsetByNamesTest extends AnyWordSpec {
  import shapeless.{the, HNil, ::}
  "SubsetByNames" when {
    "called on HNil" should {
      "return empty" in {
        import shapeless.{the, HNil}
        val f = the[SubsetByNames[HNil, "foo" :: HNil]]
        the[f.MappedTo <:< HNil]
      }
    }
    "called on HCons having one matching name" should {
      "return with the right type" in {
        import scsc.TestPartitioning
        import scsc.Column
        import scsc.CqlType.TEXT
        val f1 = the[SubsetByNames[TestPartitioning, "foo" :: HNil]]
        the[f1.MappedTo <:< (Column["foo", TEXT] :: HNil)]
      }
    }
    "called on HCons having two matching names" should {
      "return with the right types" in {
        import scsc.TestPartitioning
        import scsc.Column
        import scsc.CqlType._
        val f1 = the[SubsetByNames[TestPartitioning, "foo" :: "bar" :: HNil]]
        the[f1.MappedTo <:< (Column["foo", TEXT] :: Column["bar", INT] :: HNil)]
      }
    }
  }
}
