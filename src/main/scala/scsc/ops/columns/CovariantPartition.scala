package scsc.ops.columns

import shapeless.{::, <:!<, HList, HNil}

trait CovariantPartition[L <: HList, P] {
  type Left <: HList
  type Right <: HList
  type Out = (Left, Right)

  def apply(l: L): Out = (filter(l), filterNot(l))

  def filter(l: L): Left

  def filterNot(l: L): Right
}

object CovariantPartition {

  type Aux[L <: HList, U, Left0 <: HList, Right0 <: HList] = CovariantPartition[L, U] {
    type Left = Left0
    type Right = Right0
  }

  def apply[L <: HList, P](implicit covariantPartition: CovariantPartition[L, P]): Aux[L, P, covariantPartition.Left, covariantPartition.Right] = covariantPartition

  implicit def hNilPartition[P]: Aux[HNil, P, HNil, HNil] = new CovariantPartition[HNil, P] {
    type Left = HNil
    type Right = HNil

    def filter(l: HNil): HNil = HNil

    def filterNot(l: HNil): HNil = HNil
  }

  implicit def hListPartition1[H, T <: HList, TLeft <: HList, TRight <: HList](implicit covariantPartition: Aux[T, H, TLeft, TRight]): Aux[H :: T, H, H :: TLeft, TRight] = new CovariantPartition[H :: T, H] {
    type Left = H :: TLeft
    type Right = TRight

    def filter(l: H :: T): Left = l.head :: covariantPartition.filter(l.tail)

    def filterNot(l: H :: T): Right = covariantPartition.filterNot(l.tail)
  }

  implicit def hListPartition2[H, T <: HList, P, TLeft <: HList, TRight <: HList](implicit covariantPartition: Aux[T, P, TLeft, TRight],
                                                                                  ev: H <:!< P): Aux[H :: T, P, TLeft, H :: TRight] = new CovariantPartition[H :: T, P] {
    type Left = TLeft
    type Right = H :: TRight

    def filter(l: H :: T): Left = covariantPartition.filter(l.tail)

    def filterNot(l: H :: T): Right = l.head :: covariantPartition.filterNot(l.tail)
  }
}
