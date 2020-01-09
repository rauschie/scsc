package scsc.ops.hlist

import shapeless.ops.hlist.{Mapper, Prepend}
import shapeless.{HList, HNil}

trait ColumnContext[L <: HList] {
  type Out <: HList
  type Unwrapped <: HList

  def map: L => Out
}

object ColumnContext {

  type Aux[L <: HList,
    O <: HList,
    U <: HList] = ColumnContext[L] {
    type Out = O
    type Unwrapped = U
  }

  trait KeyColumnContext[L <: HList] extends ColumnContext[L]

  trait OptionalColumnContext[L <: HList] extends ColumnContext[L]

  object KeyColumnContext {

    import scsc.polymappings.toKeyColumns

    implicit def keyColumnsContext[P0 <: HList, P <: HList](implicit mapper: Mapper.Aux[toKeyColumns.type, P0, P],
                                                            extractUnderlying: ExtractUnderlying[P]): Aux[P0, P, extractUnderlying.Out] = new ColumnContext[P0] {
      type Out = P
      type Unwrapped = extractUnderlying.Out

      def map: P0 => P = _ map toKeyColumns
    }
  }

  object OptionalColumnContext {

    import scsc.polymappings.toOptionalColumns

    implicit def optionalColumnsContext[O0 <: HList, O <: HList](implicit mapper: Mapper.Aux[toOptionalColumns.type, O0, O],
                                                                 extractUnderlying: ExtractUnderlying[O]): Aux[O0, O, extractUnderlying.Out] = new ColumnContext[O0] {
      type Out = O
      type Unwrapped = extractUnderlying.Out

      def map: O0 => O = _ map toOptionalColumns
    }
  }

  /*implicit def masghsgd[P0 <: HList,
    P <: HList,
    C0 <: HList,
    C <: HList,
    K0 <: HList](implicit partitioningMapper: Mapper.Aux[KeyContext.type, P0, P],
                 clusteringMapper: Mapper.Aux[KeyContext.type, C0, C],
                 prepend: Prepend.Aux[P, C, K0],
                 extractUnderlying: ExtractUnderlying[K0]): Aux[P0, C0, HNil, P, C, HNil, extractUnderlying.Out, extractUnderlying.Out] = new ColumnContext[P0, C0, HNil] {
    type PartitioningColumns = P
    type ClusteringColumns = C
    type OptionalColumns = HNil
    type Key = extractUnderlying.Out
    type Record = extractUnderlying.Out

    val mapPartitioningColumns: P0 => P = _ map KeyContext

    val mapClusteringColumns: C0 => C = _ map KeyContext

    val mapOptionalColumns: HNil => HNil = _ => HNil
  }*/
}
