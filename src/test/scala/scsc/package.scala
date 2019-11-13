
import com.datastax.driver.core._
import shapeless.{HNil,::}

package object scsc {
  implicit val session: Session = new Cluster
  .Builder()
    .addContactPoints("localhost")
    .withPort(9042)
    .build()
    .connect()
}

