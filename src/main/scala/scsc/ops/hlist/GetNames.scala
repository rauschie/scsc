package scsc.ops.hlist

import scsc.ops.UnaryTypeMapping
import shapeless.{::, HList, HNil}

sealed trait GetNames[L] extends UnaryTypeMapping[L] {
  type MappedTo <: HList
  def apply(): List[String]
}

object GetNames {

  import scsc.Column

  type Aux[L, O <: HList] = GetNames[L] {
    type MappedTo = O
  }
  implicit def getHConsNames[N <: Singleton with String, H, T <: HList](
      implicit ev: H <:< Column[N, _],
      name: ValueOf[N],
      tailNames: GetNames[T]
  ): Aux[H :: T, N :: tailNames.MappedTo] = new GetNames[H :: T] {

    import shapeless.ops.hlist.Partition

    type MappedTo = N :: tailNames.MappedTo
    Partition

    def apply(): List[String] = name.value :: tailNames()
  }

  implicit object getHNilName extends GetNames[HNil] {
    type MappedTo = HNil

    def apply(): List[String] = Nil
  }
}
