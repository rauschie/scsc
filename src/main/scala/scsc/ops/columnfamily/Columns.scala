package scsc.ops.columnfamily

import scsc.ColumnFamily
import shapeless.HList
import shapeless.ops.hlist.Prepend

import scala.language.reflectiveCalls

trait Columns[P <: HList, C <: HList, O <: HList] {
  type Out <: HList

  def apply(table: ColumnFamily[P, C, O]): Out
}

object Columns {
  type Aux[A <: HList, B <: HList, C <: HList, O <: HList] = Columns[A, B, C] {type Out = O}

  implicit def apply[A <: HList, B <: HList, C <: HList, K <: HList](implicit keyColumns: KeyColumns.Aux[A, B, K],
                                                                     prepend: Prepend[K, C]): Columns.Aux[A, B, C, prepend.Out] = new Columns[A, B, C] {
    type Out = prepend.Out

    def apply(table: ColumnFamily[A, B, C]): Out = prepend(keyColumns(table), table.optionalColumns)
  }
}
