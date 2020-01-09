package scsc


import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SessionEmbeddedTest extends CassandraTestSuite {
  "An embedded Cassandra instance" when {
    "instantiated" should {
      "be reachable by a CqlSession" in withSession{session=>
        session.getName
      }
    }
  }
}