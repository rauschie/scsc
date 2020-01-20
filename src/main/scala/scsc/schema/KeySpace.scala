package scsc.schema

import com.datastax.oss.driver.api.core.CqlIdentifier
import scala.concurrent.Future

trait KeySpace {

  import com.datastax.oss.driver.api.core.CqlSession
  import scsc.ops.Context
  import scsc.schema
  import shapeless.{HList, HNil}

  import scala.concurrent.ExecutionContextExecutor

  import scsc.schema.ColumnFamily
  val cqlId: CqlIdentifier
  protected val session: CqlSession
  implicit val ec: ExecutionContextExecutor

  def getColumnFamily[P <: HList, C <: HList, O <: HList](name: String)(
      partitioningColumns: P,
      clusteringColumns: C = HNil
  )(optionalColumns: O = HNil)(
      implicit context: Context[P, C, O]
  ): Future[
    ColumnFamily.Aux[context.Key, context.Record, context.KeyColumnNames, context.ColumnNames]
  ] = Future {
    ColumnFamily.create(name)(partitioningColumns, clusteringColumns)(optionalColumns)(context)
  }

  /**
    * Drops the given column family from this keyspace, if it exists.
    * @param columnFamily the ColumnFamily to drop
    * @return
    */
  def drop(columnFamily: ColumnFamily[_, _]): Future[Unit]
}

object KeySpace {}
