package scsc.ops.columnfamily

import scsc.ColumnFamily
import shapeless.{HList, ::, HNil}
import shapeless.ops.hlist.Prepend

trait KeyColumns[P <: HList, C <: HList] {
  type Out <: HList

  def apply(table: ColumnFamily[P, C, _]): Out
}

object KeyColumns {
  type Aux[P <: HList, C <: HList, O <: HList] = KeyColumns[P, C] {type Out = O}

  implicit def apply[P <: HList, C <: HList](implicit prepend: Prepend[P, C]): KeyColumns.Aux[P, C, prepend.Out] = new KeyColumns[P, C] {
    type Out = prepend.Out

    def apply(table: ColumnFamily[P, C, _]): Out = prepend(table.partitioningColumns, table.clusteringColumns)
  }
}

