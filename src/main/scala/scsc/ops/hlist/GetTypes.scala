package scsc.ops.hlist

import scsc.Column
import scsc.ops.UnaryTypeMapping
import shapeless.{::, HList, HNil}

sealed trait GetTypes[L] extends UnaryTypeMapping[L] {
  type MappedTo <: HList
}

object GetTypes {
  type Aux[L, O0 <: HList] = GetTypes[L] { type MappedTo = O0 }

  implicit def hConsGetTypes[A, H, T <: HList](
      implicit ev: H <:< Column[_, A],
      extractTail: GetTypes[T]
  ): Aux[H :: T, A :: extractTail.MappedTo] = new GetTypes[H :: T] {
    type MappedTo = A :: extractTail.MappedTo
  }

  implicit object hNilGetTypes extends GetTypes[HNil] {
    type MappedTo = HNil
  }
}
