package scsc.ops

import shapeless.HList

trait Context[P0, C0, O0] {

  import scsc.ColumnSet

  type K <: HList
  type V <: HList
  val keyColumns: ColumnSet[K]
  val valueColumns: ColumnSet[V]
}

object Context {

  import scsc.ops.hlist.{SetUnion, ToKeyColumns, ToOptionalColumns}
  import scsc.ColumnSet

  type Aux[P0, C0, O0, K0 <: HList, V0 <: HList] =
    Context[P0, C0, O0] {
      type K = K0
      type V = V0
    }

  implicit def context[P0, P <: HList, C0, C <: HList, O0, O <: HList, K0 <: HList, V0 <: HList](
      implicit ev: ToKeyColumns.Aux[P0, P],
      ev1: ToKeyColumns.Aux[C0, C],
      partitioning: ColumnSet[P],
      clustering: ColumnSet[C],
      ev2: SetUnion.Aux[P, C, K0],
      keyColumnSet: ColumnSet[K0],
      ev3: ToOptionalColumns.Aux[O0, O],
      ev4: SetUnion.Aux[K0, O, V0],
      valueColumnSet: ColumnSet[V0]
  ): Aux[P0, C0, O0, K0, V0] = new Context[P0, C0, O0] {
    type K = K0
    type V = V0
    val keyColumns: ColumnSet[K] = keyColumnSet
    val valueColumns: ColumnSet[V] = valueColumnSet
  }
}
