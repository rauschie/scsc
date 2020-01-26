package scsc.ops

import shapeless.HList

trait Context[P0 <: HList, C0 <: HList, O0 <: HList] {

  type KeyColumnNames <: HList
  type ColumnNames <: HList
  type Key <: HList
  type Record <: HList
}

object Context {

  type Aux[P0 <: HList, C0 <: HList, O0 <: HList, KN <: HList, K <: HList, CN, R <: HList] =
    Context[P0, C0, O0] {
      type Key = K
      type Record = R
      type KeyColumnNames = KN
      type ColumnNames = CN
    }

  implicit def context[P0 <: HList, C0 <: HList, O0 <: HList](
      implicit describeKey: KeyDescriptor[P0, C0],
      describeRecord: RecordDescriptor[P0, C0, O0]
  ): Aux[P0,
         C0,
         O0,
         describeKey.KeyColumnNames,
         describeKey.Key,
         describeRecord.ColumnNames,
         describeRecord.Record] =
    new Context[P0, C0, O0] {
      type KeyColumnNames = describeKey.KeyColumnNames
      type Key = describeKey.Key
      type ColumnNames = describeRecord.ColumnNames
      type Record = describeRecord.Record
    }

  sealed trait KeyDescriptor[P0, C0] {
    type KeyColumnNames <: HList
    type Key <: HList
  }

  sealed trait RecordDescriptor[P0, C0, O0] {
    type ColumnNames <: HList
    type Record <: HList
  }

  object KeyDescriptor {

    type Aux[P0, C0, KN <: HList, K <: HList] = KeyDescriptor[P0, C0] {
      type KeyColumnNames = KN
      type Key = K
    }

    import scsc.ops.hlist.{AsColumns, ToKey}
    import shapeless.ops.hlist.Prepend
    import shapeless.HList

    implicit def keyDescriptor[P0,
                               C0,
                               P <: HList,
                               C <: HList,
                               UP <: HList,
                               UC <: HList,
                               PN <: HList,
                               CN <: HList](
        implicit ev: P0 ToKey P,
        ev1: C0 ToKey C,
        asPartitioningColumns: AsColumns.Aux[P, UP, PN],
        asClusteringColumns: AsColumns.Aux[C, UC, CN],
        prependKey: UP Prepend UC,
        prependNames: PN Prepend CN
    ): Aux[P0, C0, prependNames.Out, prependKey.Out] =
      new KeyDescriptor[P0, C0] {
        type KeyColumnNames = prependNames.Out
        type Key = prependKey.Out
      }
  }

  object RecordDescriptor {

    import scsc.ops.hlist.{AsColumns, ToOptional}
    import shapeless.ops.hlist.Prepend

    type Aux[P0, C0, O0, CN <: HList, R <: HList] = RecordDescriptor[P0, C0, O0] {
      type ColumnNames = CN
      type Record = R
    }

    implicit def recordDescriptor[P0,
                                  C0,
                                  KN <: HList,
                                  K <: HList,
                                  O0,
                                  O <: HList,
                                  UO <: HList,
                                  ON <: HList](
        implicit key: KeyDescriptor.Aux[P0, C0, KN, K],
        ev: O0 ToOptional O,
        asOptionalColumns: AsColumns.Aux[O, UO, ON],
        prependNames: KN Prepend ON,
        prependRecord: K Prepend UO
    ): Aux[P0, C0, O0, prependNames.Out, prependRecord.Out] =
      new RecordDescriptor[P0, C0, O0] {
        type ColumnNames = prependNames.Out
        type Record = prependRecord.Out
      }
  }
}
