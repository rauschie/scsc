package scsc.ops.boundstatement

import com.datastax.oss.driver.api.core.cql.BoundStatement
import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.syntax.BoundStatementOps
import scsc.EmbeddedCassandraTestSuite
import shapeless.{::, the, HNil}

@RunWith(classOf[JUnitRunner])
class SetColumnValuesEmbeddedTest extends AnyWordSpec with EmbeddedCassandraTestSuite {
//  implicit val keyTxtCtx: TypeContext.Aux[TEXT, String] = the[TypeContext.Aux[TEXT, String]]
  //  implicit val optTxtCtx:TypeContext.Aux[TEXT, Option[String]] = the[OptionalContext[TEXT]]
  //implicit val keyIntCtx: TypeContext.Aux[INT, Int] = the[TypeContext.Aux[INT, Int]]
  //implicit val optIntCtx:TypeContext.Aux[INT, Option[Int]] = the[OptionalContext[INT]]
  "SetToRecord" when {
    import scsc.ops.hlist.SetColumnValues
    val emptySetter = the[SetColumnValues[HNil]]

    "summoned" must {
      "set values" in withDefaultTable { session =>
        import scsc.Column
        val boundStatement: BoundStatement = session.prepare("insert into cyclist_by_year_and_name " +
                                                               "(race_year, race_name, rank, cyclist_name) " +
                                                               "values (?, ?, ?, ?)")
                                                    .bind()
        implicit val Setter = the[SetColumnValues.Aux[Column["race_year", Int] :: Column["race_name", String] :: Column["rank", Int] :: Column["cyclist_name", String] :: HNil,
          Int :: String :: Int :: String :: HNil]]
        boundStatement.setToRecord(2000 :: "foo" :: 2 :: "bar" :: HNil)

      }
    }
  }
}
