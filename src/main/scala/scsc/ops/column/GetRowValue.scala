package scsc.ops.column

import com.datastax.oss.driver.api.core.cql.Row
import scsc.Column
import scsc.ops.Get

sealed trait GetRowValue[C <: Column] {
  type V

  def apply(column: C, row: Row): V
}

object GetRowValue {
  type Aux[C <: Column.BackedBy[VO], VO] = GetRowValue[C] {type V = VO}

  implicit def getKeyCell[A](implicit get: Get[A]): GetRowValue.Aux[Column.BackedBy[A], A] = new GetRowValue[Column.BackedBy[A]] {
    type V = A

    def apply(column: Column.BackedBy[A], row: Row): V = get(row, column.name)
  }

  implicit def getOptionalCell[A](implicit get: Get[A]): GetRowValue.Aux[Column.BackedBy[Option[A]], Option[A]] = new GetRowValue[Column.BackedBy[Option[A]]] {
    type V = Option[A]

    def apply(column: Column.BackedBy[Option[A]], row: Row): V = if (row.isNull(column.name)) None else Some(get(row, column.name))
  }
}