package scsc.ops.columns

import com.datastax.oss.driver.api.core.cql.Row
import scsc.Column
import scsc.ops.column.GetRowValue
import shapeless.{::, HList, HNil}

trait GetRecord[L <: HList] {
  type Out <: HList

  def apply(columns: L, row: Row): Out
}

object GetRecord extends LowPriorityGetRecord {

  type Aux[L <: HList, O] = GetRecord[L] {type Out = O}

  def apply[L <: HList](implicit getRecord: GetRecord[L]): GetRecord[L] = getRecord

  implicit def getNonEmptyRecord[H <: Column, T <: HList](implicit getCell: GetRowValue[H],
                                                          getRecord: GetRecord[T]): Aux[H :: T, getCell.V :: getRecord.Out] =
    new GetRecord[H :: T] {
      type Out = getCell.V :: getRecord.Out

      def apply(columns: H :: T, row: Row): getCell.V :: getRecord.Out = getCell(columns.head, row) :: getRecord(columns.tail, row)
    }
}

trait LowPriorityGetRecord {
  /*TODO
this probably isnt necessary
 */
  implicit def getEmptyRecord: GetRecord.Aux[HNil, HNil] =
    new GetRecord[HNil] {
      type Out = HNil

      def apply(columns: HNil, row: Row): Out = HNil
    }
}