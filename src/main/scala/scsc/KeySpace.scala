package scsc



trait KeySpace {

  import scsc.ops.Context
  import shapeless.{HList, HNil}


  //todo impl
  def getColumnFamily[P <: HList, C <: HList, O <: HList](name: String)(
      partitioningColumns: P,
      clusteringColumns: C = HNil
  )(optionalColumns: O = HNil)(
      implicit context: Context[P, C, O]
  ): Transaction[
    ColumnFamily[context.K,context.V]
  ]

  /**
    * Drops the given column family from this keyspace, if it exists.
    * @param columnFamily the ColumnFamily to drop
    * @return
    */
  def drop(columnFamily: ColumnFamily[_, _]): Transaction[Unit]
}

object KeySpace {}
