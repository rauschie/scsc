package scsc.ops.hlist

import scsc.ops.UnaryTypeMapping
import shapeless.{::, HList, HNil}

sealed trait GetNames[L] extends UnaryTypeMapping[L] {
  type MappedTo <: HList
}

object GetNames {

  import scsc.Column

  type Aux[L, O <: HList] = GetNames[L] {
    type MappedTo = O
  }
  implicit def getHConsNames[N <: Singleton with String, H, T <: HList](
      implicit ev: H <:< Column[N, _],
      tailNames: GetNames[T]
  ): Aux[H :: T, N :: tailNames.MappedTo] = new GetNames[H :: T] {

    import shapeless.ops.hlist.Partition

    type MappedTo = N :: tailNames.MappedTo
    Partition
  }

  implicit object getHNilName extends GetNames[HNil] {
    type MappedTo = HNil
  }
}
