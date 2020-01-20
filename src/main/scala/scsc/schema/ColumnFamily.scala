package scsc.schema

import com.datastax.oss.driver.api.core.CqlIdentifier
import shapeless.HList

sealed trait ColumnFamily[K <: HList, R <: HList] {

  type Key = K
  type Record = R
  type KeyColumnNames <: HList
  type ColumnNames <: HList
  // val keySpace: KeySpace
  val cqlId: CqlIdentifier
  /*
  def put(record: Record): Future[Unit]
  def put(records: Iterable[Record]): Future[Unit]

  def get(key: Key): Future[Record]
  def get(keys: Iterable[Key]): Future[Iterable[Record]]

  def delete(key: Key): Future[Unit]
  def delete(keys: Iterable[Key]): Future[Unit]
 */

}

object ColumnFamily {

  import scsc.ops.Context
  import shapeless.HNil

  type Aux[K <: HList, R <: HList, KN <: HList, CN <: HList] = ColumnFamily[K, R] {
    type KeyColumnNames = KN
    type ColumnNames = CN
  }

  private[schema] def createFromPartitioningColumns[P <: HList](n: String /*,ks: KeySpace*/ )(
      partitioningColumns: P
  )(
      implicit context: Context[P, HNil, HNil]
  ): Aux[context.Key, context.Record, context.KeyColumnNames, context.ColumnNames] =
    new ColumnFamily[context.Key, context.Record] {
      type KeyColumnNames = context.KeyColumnNames
      type ColumnNames = context.ColumnNames
      // val keySpace: KeySpace = ks
      val cqlId: CqlIdentifier = CqlIdentifier.fromCql(n)
    }

  private[schema] def create[P <: HList, C <: HList, O <: HList](n: String /*, ks: KeySpace*/
  )(partitioningColumns: P, clusteringColumns: C)(optionalColumns: O)(
      implicit context: Context[P, C, O]
  ): Aux[context.Key, context.Record, context.KeyColumnNames, context.ColumnNames] =
    new ColumnFamily[context.Key, context.Record] {
      type KeyColumnNames = context.KeyColumnNames
      type ColumnNames = context.ColumnNames
      //  val keySpace: KeySpace = ks
      val cqlId: CqlIdentifier = CqlIdentifier.fromCql(n)
    }

  private[schema] def createFromKeyColumns[P <: HList, C <: HList](n: String /*, ks: KeySpace*/ )(
      partitioningColumns: P,
      clusteringColumns: C
  )(
      implicit context: Context[P, C, HNil]
  ): Aux[context.Key, context.Record, context.KeyColumnNames, context.ColumnNames] =
    new ColumnFamily[context.Key, context.Record] {
      type KeyColumnNames = context.KeyColumnNames
      type ColumnNames = context.ColumnNames
      //val keySpace: KeySpace = ks
      val cqlId: CqlIdentifier = CqlIdentifier.fromCql(n)
    }

  private[schema] def createFromOptionalColumns[P <: HList, O <: HList](n: String /*, ks: KeySpace*/
  )(
      partitioningColumns: P,
      optionalColumns: O
  )(
      implicit context: Context[P, HNil, O]
  ): Aux[context.Key, context.Record, context.KeyColumnNames, context.ColumnNames] =
    new ColumnFamily[context.Key, context.Record] {
      type KeyColumnNames = context.KeyColumnNames
      type ColumnNames = context.ColumnNames
      //val keySpace: KeySpace = ks
      val cqlId: CqlIdentifier = CqlIdentifier.fromCql(n)
    }
}
