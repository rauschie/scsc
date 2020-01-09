package scsc.ops

import shapeless.HList

import scsc.ops.crud.Create

//import scsc.ops.crud.Put
trait Context[P0 <: HList, C0 <: HList, O0 <: HList] {

  type KeyColumns <: HList
  type AllColumns <: HList
  type Key <: HList
  type Record <: HList
  val create: Create[P0, C0, O0]
  //val put: Put[KeyColumns, OptionalColumns]
}

object Context {

  import scsc.ops.crud.Put

  type Aux[P0 <: HList, C0 <: HList, O0 <: HList,
    KC <: HList,
    K <: HList, R <: HList] = Context[P0, C0, O0] {
    type KeyColumns = KC
    type Key = K
    type Record = R
  }

  implicit def context[P0 <: HList, C0 <: HList, O0 <: HList,
    K <: HList, O <: HList,
    KeyFields <: HList, OptionalFields <: HList](implicit describeKey: KeyDescriptor[P0, C0],
                                                 describeRecord: RecordDescriptor[P0, C0, O0],
                                                 c: Create[P0, C0, O0],
                                                 p: Put[K, O]): Aux[P0, C0, O0, describeKey.Columns, describeKey.Key, describeRecord.Record] = new Context[P0, C0, O0] {
    type KeyColumns = describeKey.Columns
    type Key = describeKey.Key
    type Record = describeRecord.Record
    val create: Create[P0, C0, O0] = c
    //  val put: Put[K, O] = p
  }


  sealed trait KeyDescriptor[P0, C0] {
    type Columns <: HList
    type Key <: HList
  }

  sealed trait RecordDescriptor[P, C, O] {
    type Columns <: HList
    type Record <: HList
  }

  object KeyDescriptor {
    type Aux[P0, C0, KC <: HList, K <: HList] = KeyDescriptor[P0, C0] {
      type KeyColumns = KC
      type Key = K
    }

    import scsc.ops.hlist.{Extract, ToKey}
    import shapeless.ops.hlist.Prepend
    import shapeless.HList

    implicit def keyDescriptor[P0, C0,
      P <: HList, C <: HList,
      UP <: HList, UC <: HList](implicit ev1: P0 ToKey P,
                                ev2: C0 ToKey C,
                                ev3: P Extract UP,
                                ev4: C Extract UC,
                                prependColumns: P Prepend C,
                                prependKey: UP Prepend UC): Aux[P0, C0, prependColumns.Out, prependKey.Out] = new KeyDescriptor[P0, C0] {
      type KeyColumns = prependColumns.Out
      type Key = prependKey.Out
    }
  }

  object RecordDescriptor {

    import scsc.ops.hlist.{Extract, ToOptional}
    import shapeless.ops.hlist.Prepend

    type Aux[P, C, O, C0 <: HList, R <: HList] = RecordDescriptor[P, C, O] {
      type Columns = C0
      type Record = R
    }

    implicit def recordDescriptor[P0, C0, KC <: HList, K <: HList, O0, O <: HList, UO <: HList](implicit key: KeyDescriptor.Aux[P0, C0, KC, K],
                                                                                                ev: O0 ToOptional O,
                                                                                                ev1: O Extract UO,
                                                                                                columnsPrepend: KC Prepend O,
                                                                                                recordPrepend: K Prepend UO): Aux[P0, C0, O0, columnsPrepend.Out, recordPrepend.Out] = new RecordDescriptor[P0, C0, O0] {
      type Columns = columnsPrepend.Out
      type Record = recordPrepend.Out
    }
  }

}
