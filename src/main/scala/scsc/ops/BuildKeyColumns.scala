package scsc.ops

import scsc.CqlType.DOUBLE
import scsc.{Column, ColumnBlueprint, CqlType}
import shapeless.{::, HList, HNil}
import scsc.syntax.CqlTypeOps

trait BuildKeyColumns[L <: HList] {
  type Out <: HList

  def apply(columnBlueprints: L): Out
}

object BuildKeyColumns extends LowPriorityBuildKeyColumns {

  type Aux[L <: HList, O <: HList] = BuildKeyColumns[L] {type Out = O}

  implicit def buildNonEmptyBlueprints[H <: ColumnBlueprint,
    T <: HList,
    N <: Singleton with String,
    J](implicit ev: H <:< ColumnBlueprint.Aux[J, N],
       buildTail: BuildKeyColumns[T]): BuildKeyColumns.Aux[H :: T, Column.Aux[J, N] :: buildTail.Out] = new BuildKeyColumns[H :: T] {
    type Out = Column.Aux[J, N] :: buildTail.Out

    def apply(columnBlueprints: H :: T): Out = {
      val head: ColumnBlueprint.Aux[J, N] = columnBlueprints.head
      Column.key[N](head.cqlType, head.name) :: buildTail(columnBlueprints.tail)
    }
  }
}

trait LowPriorityBuildKeyColumns {
  /*TODO
  this probably isnt necessary
   */
  implicit def buildEmptyBlueprint: BuildKeyColumns.Aux[HNil, HNil] = new BuildKeyColumns[HNil] {
    type Out = HNil

    def apply(columnBlueprints: HNil): Out = HNil

  }
}
