package scsc.ops.row

import com.datastax.oss.driver.api.core.cql.Row
import scsc.{Column, CqlType}
import shapeless.{::, HList, HNil}

trait GetRecord[L <: HList] {
  type Out <: HList

  def apply(columns: L, row: Row): Out
}

object GetRecord {

  type Aux[L <: HList, O] = GetRecord[L] {type Out = O}

  def apply[L <: HList](implicit getRecord: GetRecord[L]): GetRecord[L] = getRecord

/*  implicit def getNonEmptyRecord[H <: Column.Aux[N, C],
    T <: HList,
    N <: Singleton with String,
    C<:CqlType,
    M: Get](implicit ctx: TypeContext.Aux[C, M],
            name: ValueOf[N],
            getRecord: GetRecord[T]): Aux[H :: T, M :: getRecord.Out] = new GetRecord[H :: T] {
    type Out = ctx.MappedTo :: getRecord.Out

    def apply(columns: H :: T, row: Row): ctx.MappedTo :: getRecord.Out = row.getValueIn(columns.head) :: getRecord(columns.tail, row)
  }*/

  implicit def getEmptyRecord: GetRecord[HNil] =
    new GetRecord[HNil] {
      type Out = HNil

      def apply(columns: HNil, row: Row): Out = HNil
    }
}