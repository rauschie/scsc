package scsc.ops.boundstatement

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.cql.BoundStatement
import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import scsc.EmbeddedCassandraTestSuite
import shapeless.the

@RunWith(classOf[JUnitRunner])
class SetterEmbeddedTestEmbedded extends AnyWordSpec with EmbeddedCassandraTestSuite {
  "a Setter" when {
    "implemented" must {
      "be implicitly available" in {
        the[Setter[Double]]
      }
      "set value" in withDefaultTable { session =>

        val boundStatement: BoundStatement = session.prepare("insert into cyclist_by_year_and_name " +
                                                               "(race_year, race_name, rank, cyclist_name) " +
                                                               "values (?, ?, ?, ?)")
                                                    .bind()
        val race_year_Set = the[Setter[Int]].setValueTo(boundStatement, CqlIdentifier.fromCql("race_year"), 2000)
        val race_name_Set = the[Setter[String]].setValueTo(race_year_Set, CqlIdentifier.fromCql("race_name"), "foo")
        val rank_Set = the[Setter[Int]].setValueTo(race_name_Set, CqlIdentifier.fromCql("rank"), 5)
        val cyclist_name_Set = the[Setter[String]].setValueTo(rank_Set, CqlIdentifier.fromCql("cyclist_name"), "Dickey Dope")
        session.execute(cyclist_name_Set)

      }
    }
  }

}
