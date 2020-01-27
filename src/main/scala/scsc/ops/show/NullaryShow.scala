package scsc.ops.show

import cats.Show

trait NullaryShow[A] extends Show[A] {
  def apply(): String
}

object NullaryShow {
  import scsc.CqlDataType
  implicit def cqlTypeShow[A<:CqlDataType](implicit show: CqlDataTypeShow[A]): NullaryShow[A] = show
  implicit def singletonStringShow[A<:String](implicit show: SingletonStringShow[A]): NullaryShow[A] = show
}
