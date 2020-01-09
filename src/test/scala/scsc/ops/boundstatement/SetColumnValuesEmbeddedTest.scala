package scsc.ops.boundstatement

import com.datastax.oss.driver.api.core.cql.BoundStatement
import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.Column.Aux
import scsc.CqlType.{ASCII, BOOLEAN, INT, TEXT}
import scsc.syntax.BoundStatementOps
import scsc.{Column, EmbeddedCassandraTestSuite}
import shapeless.{::, the, HNil}

@RunWith(classOf[JUnitRunner])
class SetColumnValuesEmbeddedTestEmbedded extends AnyWordSpec with EmbeddedCassandraTestSuite {


//  implicit val keyTxtCtx: TypeContext.Aux[TEXT, String] = the[TypeContext.Aux[TEXT, String]]
  //  implicit val optTxtCtx:TypeContext.Aux[TEXT, Option[String]] = the[OptionalContext[TEXT]]
  //implicit val keyIntCtx: TypeContext.Aux[INT, Int] = the[TypeContext.Aux[INT, Int]]
  //implicit val optIntCtx:TypeContext.Aux[INT, Option[Int]] = the[OptionalContext[INT]]
  "SetToRecord" when {
    import scsc.ops.hlist.SetColumnValues
    val emptySetter = the[SetColumnValues[HNil]]

    "summoned" must {
      "set values" in withDefaultTable { session =>
        val boundStatement: BoundStatement = session.prepare("insert into cyclist_by_year_and_name " +
                                                               "(race_year, race_name, rank, cyclist_name) " +
                                                               "values (?, ?, ?, ?)")
                                                    .bind()
        implicit val Setter = the[SetColumnValues.Aux[Aux[Int, "race_year"] :: Aux[String, "race_name"] :: Aux[Int, "rank"] :: Aux[String, "cyclist_name"] :: HNil,
          Int :: String :: Int :: String :: HNil]]
        boundStatement.setToRecord(2000 :: "foo" :: 2 :: "bar" :: HNil)

      }
    }
  }
}
