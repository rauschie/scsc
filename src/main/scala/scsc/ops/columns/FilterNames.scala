package scsc.ops.columns

import scsc.Column
import shapeless.ops.hlist.Prepend
import shapeless.{::, HList, HNil}

sealed trait FilterNames[L <: HList, N <: HList] {
  type Out <: HList

  def apply(columns: L, names: N): Out
}

object FilterNames {
  type Aux[L <: HList, N <: HList, Out0 <: HList] = FilterNames[L, N] {type Out = Out0}

  /*
  L empty? =>ret
  N empty? =>ret
  else filter [L, n.head]

  0 0 HNil
  0 1 HNil
  1 0 1
  1 1 loop

  Aux[HNil,NH::NT,O]
  Aux[LH::LT,HNil,O]
   */

  implicit def hConsColumnsWithHConsNames[
    L <: HList,
    H <: Singleton with String,
    T <: HList,
    F <:HList,
  ](implicit covariantPartition: CovariantPartition[L, Column.Named[H]],
    filterNames: FilterNames.Aux[L, T, F],
    prepend: Prepend[L, ]): Aux[L, H :: T, prepend.Out] =

    new FilterNames[HL :: TL, H :: T] {
      type Out = prepend.Out

      def apply(columns: HL :: TL, names: H :: T): Out = covariantPartition.filter(columns) ::: filterNames(columns.tail, names.tail)
    }

  val a = implicitly[FilterNames[Column.Named["foo"] :: HNil, "foo"]]

  /*
  TODO
  need to figure out if below implicits clash
   */
  implicit def hConsColumnsWithHNilNames[H, T <: HList]: Aux[H :: T, HNil, HNil] = ???

  implicit def hNilColumnsWithHConsNames[H, T <: HList]: Aux[HNil, H :: T, HNil] = ???
}
