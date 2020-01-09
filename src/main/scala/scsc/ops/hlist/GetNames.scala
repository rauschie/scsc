package scsc.ops.hlist

import shapeless.{HList, HNil}

sealed trait GetNames[L] {
  def apply(): List[String]
}

object GetNames {

  import scsc.Column

  implicit def getHConsNames[N <: Singleton with String, H, T <: HList](implicit ev: H <:< Column.Aux[_, N],
                                                                        name: ValueOf[N],
                                                                        getNames: GetNames[T]): GetNames[shapeless.::[H, T]] = new GetNames[shapeless.::[H, T]] {
    def apply(): List[String] = name.value :: getNames()
  }

  implicit object getHNilName extends GetNames[HNil] {
    def apply(): List[String] = Nil
  }
}
