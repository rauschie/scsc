package scsc

import org.junit.runner.RunWith
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SessionEmbeddedTest extends AnyWordSpec with EmbeddedCassandraTestSuite {
  "An embedded scsc.schema.Cassandra instance" when {
    "instantiated" should {
      "be reachable by a CqlSession" in withSession { session =>
        session.getName
      }
    }
  }
}