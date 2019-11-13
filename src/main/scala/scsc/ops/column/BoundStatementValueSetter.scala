package scsc.ops.column

import com.datastax.oss.driver.api.core.cql.BoundStatement
import scsc.Column
import scsc.ops.Set

trait BoundStatementValueSetter[C <: Column] {
  type V

  def apply(column: C): (BoundStatement, V) => BoundStatement
}

object BoundStatementValueSetter {
  type Aux[C <: Column.BackedBy[U], U] = BoundStatementValueSetter[C] {type V = U}

  implicit def keyCellSetter[A](implicit set: Set[A]): BoundStatementValueSetter.Aux[Column.BackedBy[A], A] = new BoundStatementValueSetter[Column.BackedBy[A]] {
    type V = A

    def apply(column: Column.BackedBy[A]): (BoundStatement, A) => BoundStatement = (boundStatement, v) => set(boundStatement, column.name, v)
  }

  implicit def optionalCellSetter[A](implicit set: Set[A]): BoundStatementValueSetter.Aux[Column.BackedBy[Option[A]], Option[A]] = new BoundStatementValueSetter[Column.BackedBy[Option[A]]] {
    type V = Option[A]

    def apply(column: Column.BackedBy[Option[A]]): (BoundStatement, Option[A]) => BoundStatement = (boundStatement, v) => v match {
      case Some(a) => set(boundStatement, column.name, a)
      case _ => boundStatement
    }
  }
}