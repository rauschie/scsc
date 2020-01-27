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

    //todo fix this
    type Aux[P0, C0, Kn <: HList, Ku <: HList] = KeyDescriptor[P0, C0] {
      type KeyColumnNames = Kn
      type Key = Ku
    }

    import scsc.ops.hlist.{Extract, GetNames, ToKey}
    import shapeless.ops.hlist.Prepend
    import shapeless.HList

    implicit def keyDescriptor[P0,
                               C0,
                               P <: HList,
                               C <: HList,
                               Pu <: HList,
                               Pn <: HList,
                               Cu <: HList,
                               Cn <: HList](
        implicit ev: P0 ToKey P,
        ev1: C0 ToKey C,
        partitioningTypes: P Extract Pu,
        clusteringTypes: C Extract Cu,
        partitioningNames: GetNames.Aux[P, Pn],
        clusteringNames: GetNames.Aux[C, Cn],
        prependKey: Pu Prepend Cu,
        prependNames: Pn Prepend Cn
    ): Aux[P0, C0, prependNames.Out, prependKey.Out] =
      new KeyDescriptor[P0, C0] {
        type KeyColumnNames = prependNames.Out
        type Key = prependKey.Out
      }
  }

  object RecordDescriptor {

    import scsc.ops.hlist.{Extract, GetNames, ToOptional}
    import shapeless.ops.hlist.Prepend

    //todo fix this
    type Aux[P0, C0, O0, CN <: HList, R <: HList] = RecordDescriptor[P0, C0, O0] {
      type ColumnNames = CN
      type Record = R
    }

    implicit def recordDescriptor[P0,
                                  C0,
                                  Kn <: HList,
                                  Ku <: HList,
                                  O0,
                                  O <: HList,
                                  Ou <: HList,
                                  On <: HList](
        implicit key: KeyDescriptor.Aux[P0, C0, Kn, Ku],
        ev: O0 ToOptional O,
        ev1: O Extract Ou,
        ev2: GetNames.Aux[O, On],
        prependNames: Kn Prepend On,
        prependValues: Ku Prepend Ou
    ): Aux[P0, C0, O0, prependNames.Out, prependValues.Out] =
      new RecordDescriptor[P0, C0, O0] {
        type ColumnNames = prependNames.Out
        type Record = prependValues.Out
      }
  }
}
