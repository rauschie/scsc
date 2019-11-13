package scsc

import com.datastax.driver.core.Session
import scsc.cql.{Create, InsertInto}
import scsc.ops.columnfamily.{Columns, KeyColumns}
import scsc.ops.columns.BoundStatementSetter
import shapeless.HList
import shapeless.ops.hlist.Filter

package object syntax {

  abstract class ColumnFamilyOps[P <: HList, C <: HList, O <: HList, Key<:HList, Record<:HList](val table: ColumnFamily.Aux[P, O, O, Key, Record]) {
    def columns(implicit columns: Columns[P, O, O]): columns.Out = columns(table)

    def keyColumns(implicit keyColumns: KeyColumns[P, O]): keyColumns.Out = keyColumns(table)

    def create(implicit session: Session,
               create: Create[P, O, O]): Unit = {
      session.execute(create.query(table))
    }

    def put[Cols <: HList, R <: HList](record: R)(implicit session: Session,
                                                  columns: Columns.Aux[P, O, O, Cols],
                                                  insertInto: InsertInto[P, O, O],
                                                  recordsetter: BoundStatementSetter.Aux[Cols, R]): Unit = {
      val stmt = session.prepare(insertInto.query(table)).bind()
      val set = recordsetter.apply(columns(table))
      session.execute(set(record, stmt))
    }

    def get[ColNames <: HList](key: Key, columns: ColNames)(implicit session: Session,

    ) = ???

  }

  implicit class CqlTypeOps[C <: CqlType](val cqlType: C) {
    def apply[N <: Singleton with String](name: N): ColumnBlueprint.Aux[cqlType.JavaType, N] = ColumnBlueprint[cqlType.JavaType, N](cqlType, name)
  }
  Filter
}
