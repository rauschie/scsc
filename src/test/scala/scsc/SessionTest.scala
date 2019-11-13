package scsc

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.Matchers._

@RunWith(classOf[JUnitRunner])
class SessionTest extends AnyFlatSpec {
  "A session" must "be present" in {
    noException should be thrownBy {
      session.execute("select * from system_schema.keyspaces;")
    }
  }
}
