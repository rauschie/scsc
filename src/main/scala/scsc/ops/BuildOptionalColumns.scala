package scsc.ops

import scsc.{Column, ColumnBlueprint}
import shapeless.{::, HList, HNil}

trait BuildOptionalColumns[L <: HList] {
  type Out <: HList

  def apply(columnBlueprints: L): Out
}

object BuildOptionalColumns extends LowPriorityBuildOptionalColumns {

  type Aux[L <: HList, O <: HList] = BuildOptionalColumns[L] {type Out = O}

  implicit def buildNonEmptyBlueprints[H <: ColumnBlueprint,
    T <: HList,
    N <: Singleton with String,
    J](implicit ev: H <:< ColumnBlueprint.Aux[J, N],
       buildTail: BuildOptionalColumns[T]): BuildOptionalColumns.Aux[H :: T, Column.Aux[Option[J], N] :: buildTail.Out] = new BuildOptionalColumns[H :: T] {
    type Out = Column.Aux[Option[J], N] :: buildTail.Out

    def apply(columnBlueprints: H :: T): Out = {
      val head: ColumnBlueprint.Aux[J, N] = columnBlueprints.head
      Column[N](head.cqlType, head.name) :: buildTail(columnBlueprints.tail)
    }
  }
}

trait LowPriorityBuildOptionalColumns {
  /*TODO
  this probably isnt necessary
   */
  implicit def buildEmptyBlueprint: BuildOptionalColumns.Aux[HNil, HNil] = new BuildOptionalColumns[HNil] {
    type Out = HNil

    def apply(columnBlueprints: HNil): Out = HNil

  }
}
