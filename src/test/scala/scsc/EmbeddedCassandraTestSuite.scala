package scsc

import java.net.InetSocketAddress

import com.datastax.oss.driver.api.core.CqlSession
import org.scalatest.{BeforeAndAfterAll, Suite}

trait EmbeddedCassandraTestSuite extends Suite with BeforeAndAfterAll {

  import org.cassandraunit.utils.EmbeddedCassandraServerHelper

  override def beforeAll(): Unit = {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra(15000)
  }

  /**
   * CREATE TABLE cycling.cyclist_by_year_and_name (race_year int,race_name text,rank int,cyclist_name text,PRIMARY KEY((race_year,race_name),rank))
   *
   * @param testCode
   */
  def withDefaultTable(testCode: CqlSession => Any): Unit = withKeySpace("cycling") { session =>

    import com.datastax.oss.driver.api.core.`type`.DataTypes
    import com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable

    session.execute(createTable("cyclist_by_year_and_name")
                      .ifNotExists()
                      .withPartitionKey("race_year", DataTypes.INT)
                      .withPartitionKey("race_name", DataTypes.TEXT)
                      .withClusteringColumn("rank", DataTypes.INT)
                      .withColumn("cyclist_name", DataTypes.TEXT).build())
    testCode(session)
  }

  def withKeySpace(name: String)(testCode: CqlSession => Any): Unit = {

    import com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace

    val initialSession = CqlSession
      .builder()
      .withLocalDatacenter("datacenter1")
      .addContactPoint(new InetSocketAddress("127.0.0.1", 9142))
      .build()

    initialSession.execute(createKeyspace(name)
                             .ifNotExists()
                             .withSimpleStrategy(1)
                             .build())
    initialSession.close()
    val session = CqlSession
      .builder()
      .withLocalDatacenter("datacenter1")
      .addContactPoint(new InetSocketAddress("127.0.0.1", 9142))
      .withKeyspace(name)
      .build()
    testCode(session)
    session.close()
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
  }

  def withSession(testCode: CqlSession => Any): Unit = {
    val session: CqlSession = CqlSession
      .builder()
      .withLocalDatacenter("datacenter1")
      .addContactPoint(new InetSocketAddress("127.0.0.1", 9142))
      .build()

    testCode(session)
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
  }
}
