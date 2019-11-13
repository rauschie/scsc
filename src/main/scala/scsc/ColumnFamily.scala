package scsc

import scsc.ops.{BuildKeyColumns, BuildOptionalColumns}
import shapeless.{HList, HNil}

sealed trait ColumnFamily[P <: HList, C <: HList, O <: HList] {
  type Key <: HList
  type Record <: HList
  val name: String
  val partitioningColumns: P
  val clusteringColumns: C
  val optionalColumns: O
}

object ColumnFamily {

  type Aux[P <: HList,
    C <: HList,
    O <: HList,
    K <: HList,
    R <: HList] = ColumnFamily[P, C, O] {
    type Key = K
    type Record = R}

  def apply[P0 <: HList](n: String)(partitioningBlueprints: P0)(implicit buildColumns: BuildKeyColumns[P0]): ColumnFamily[buildColumns.Out, HNil, HNil] = new ColumnFamily[buildColumns.Out, HNil, HNil] {
    type Key = HNil
    type Record = HNil
    val name: String = n
    val partitioningColumns: buildColumns.Out = buildColumns(partitioningBlueprints)
    val clusteringColumns: HNil = HNil
    val optionalColumns: HNil = HNil
  }


  def apply[P0 <: HList, O0 <: HList](n: String)(partitioningBlueprints: P0,
                                                 optionalBlueprints: O0)(implicit buildPartitioningColumns: BuildKeyColumns[P0],
                                                                         buildOptionalColumns: BuildOptionalColumns[O0]): ColumnFamily[buildPartitioningColumns.Out, HNil, buildOptionalColumns.Out] = new ColumnFamily[buildPartitioningColumns.Out, HNil, buildOptionalColumns.Out] {
    type Key = HNil
    type Record = HNil
    val name: String = n
    val partitioningColumns: buildPartitioningColumns.Out = buildPartitioningColumns(partitioningBlueprints)
    val clusteringColumns: HNil = HNil
    val optionalColumns: buildOptionalColumns.Out = buildOptionalColumns(optionalBlueprints)
  }

  def apply[P0 <: HList,
    C0 <: HList,
    O0 <: HList](n: String)(keyBlueprints: (P0, C0),
                            optionalBlueprints: O0 = HNil)(implicit buildPartitioningColumns: BuildKeyColumns[P0],
                                                           buildClusteringColumns: BuildKeyColumns[C0],
                                                           buildOptionalColumns: BuildOptionalColumns[O0]): ColumnFamily[buildPartitioningColumns.Out, buildClusteringColumns.Out, buildOptionalColumns.Out] = new ColumnFamily[buildPartitioningColumns.Out, buildClusteringColumns.Out, buildOptionalColumns.Out] {
    type Key = HNil
    type Record = HNil
    val name: String = n
    val partitioningColumns: buildPartitioningColumns.Out = buildPartitioningColumns(keyBlueprints._1)
    val clusteringColumns: buildClusteringColumns.Out = buildClusteringColumns(keyBlueprints._2)
    val optionalColumns: buildOptionalColumns.Out = buildOptionalColumns(optionalBlueprints)
  }

}

