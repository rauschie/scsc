package scsc.ops.columns

import com.datastax.driver.core.BoundStatement
import scsc.Column
import scsc.ops.column.BoundStatementValueSetter
import shapeless.{::, HList, HNil}

trait BoundStatementSetter[L <: HList] {
  type Record <: HList

  def apply(columns: L): (Record, BoundStatement) => BoundStatement
}

object BoundStatementSetter extends LowPriorityRecordSetter {
  type Aux[L <: HList, R <: HList] = BoundStatementSetter[L] {type Record = R}

  implicit def nonEmptyRecordSetter[H <: Column, T <: HList](implicit cellSetter: BoundStatementValueSetter[H],
                                                             recordSetter: BoundStatementSetter[T]): BoundStatementSetter.Aux[H :: T, cellSetter.V :: recordSetter.Record] =
    new BoundStatementSetter[H :: T] {
      type Record = cellSetter.V :: recordSetter.Record

      override def apply(columns: H :: T): (Record, BoundStatement) => BoundStatement = (record, boundStatement) =>
        cellSetter(columns.head).apply(recordSetter(columns.tail).apply(record.tail, boundStatement),
                                       record.head)
    }
}

trait LowPriorityRecordSetter {
  /*TODO
this probably isnt necessary
 */
  implicit def emptyRecordSetter: BoundStatementSetter.Aux[HNil, HNil] = new BoundStatementSetter[HNil] {
    type Record = HNil

    def apply(columns: HNil): (HNil, BoundStatement) => BoundStatement = (_, row) => row
  }
}
