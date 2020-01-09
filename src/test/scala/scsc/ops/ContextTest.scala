package scsc.ops

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.Column
import scsc.CqlType.INT
import scsc.ops.column.ColumnTypeMapping.KeyColumnTypeMapping
import shapeless.ops.hlist.{Comapped, Mapper}
import shapeless.{::, HNil, the}

@RunWith(classOf[JUnitRunner])
class ColumnContextTest extends AnyWordSpec {
  "MapColumns" when {
    "summoned" should {
      "be available" in {
 //       the[MapColumns[Column.Aux[INT, "Foo"] :: HNil, HNil, HNil]]
        the[Mapper.Aux[KeyMappingColumn.type, Column.Aux[INT, "Foo"] :: HNil, Column.Aux[Int, "Foo"] :: HNil]]
      }
    }
  }
}
