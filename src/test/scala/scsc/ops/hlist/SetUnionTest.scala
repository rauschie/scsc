package scsc.ops.hlist

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SetUnionTest extends AnyWordSpec {
  import shapeless.the
  "a SetUnion" when {
    "formed with an empty set" must {
      "return the original set" in {
        import scsc.TestKeyColumns
        import shapeless.HNil
        val union = the[SetUnion[HNil, TestKeyColumns]]
        the[union.MappedTo <:< TestKeyColumns]
      }
    }
    "formed with a non-empty set" must {
      "contain no duplicates" in {
        import scsc.TestKeyColumns
        import shapeless.IsDistinctConstraint
        val union = the[SetUnion[TestKeyColumns, TestKeyColumns]]
        the[IsDistinctConstraint[union.MappedTo]]
      }
      "contain all elements from both sets" in {
        import scsc.{TestO0, TestP0}
        import shapeless.BasisConstraint
        val union= the[SetUnion[TestP0, TestO0]]
        the[BasisConstraint[TestP0, union.MappedTo]]
        the[BasisConstraint[TestO0, union.MappedTo]]
      }
    }
  }

}
