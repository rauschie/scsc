package scsc.schema

/**
  * Holds the global CqlSession instance
  */
trait Cassandra {

  import com.datastax.oss.driver.api.core.CqlSession

  import scala.concurrent.Future

  protected val session: CqlSession

  /**
    * Provides a KeySpace with the specified name. If it doesn't exist in the cluster, it gets created.
    * @param keySpaceName
    * @return
    */
  def getKeySpace(keySpaceName: String): Future[KeySpace]

  /**
    * Drops the given KeySpace from the cluster.
    * @param keySpace
    * @return
    */
  def dropKeySpace(keySpace: KeySpace): Future[Unit]
}
