package scsc.ops.hlist

sealed trait ToStrings[L] {
  def apply(): List[String]
  def apply(l: L): List[String]
}

object ToStrings {
  //todo test this

  import scsc.ops.show.NullaryShow
  import shapeless.{::, HList, HNil}

  implicit def hConsToStrings[H, T <: HList](
      implicit show: NullaryShow[H],
      tailToStrings: ToStrings[T]
  ): ToStrings[H :: T] = new ToStrings[H :: T] {

    import cats.implicits.toShow
    def apply(list: H :: T): List[String] = list.head.show :: tailToStrings(list.tail)

    def apply(): List[String] = show() :: tailToStrings()
  }

  implicit object hNilToStrings extends ToStrings[HNil] {
    def apply(): List[String] = Nil
    def apply(hNil: HNil): List[String] = Nil
  }
}
