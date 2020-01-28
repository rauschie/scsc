package scsc

/**
  * Holds the global CqlSession instance
  */
object Cassandra {

  import com.datastax.oss.driver.api.core.CqlSession

  import scala.concurrent.Future

//todo impl
  /**
    * Provides a KeySpace with the specified name. If it doesn't exist in the cluster, it gets created.
    * @param keySpaceName
    * @return
    */
  def getKeySpace(keySpaceName: String): Transaction[KeySpace] = ???

  /**
    * Drops the given KeySpace from the cluster.
    * @param keySpace
    * @return
    */
  def dropKeySpace(keySpace: KeySpace): Transaction[Unit] = ???
}
