package scsc

import com.datastax.oss.driver.api.core.session.Session
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.funsuite.AnyFunSuite

@RunWith(classOf[JUnitRunner])
class SessionTest extends AnyFunSuite with BeforeAndAfter {

  before {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra(5000)
    implicit val session: Session = new Cluster
    .Builder()
      .addContactPoints("localhost")
      .withPort(9142)
      .build()
      .connect()
  }
  test("test runs"){
    println("test is running!")
  }
  after {
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
  }
}
