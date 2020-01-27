package scsc.ops.show

import cats.Show

trait NullaryShow[A] extends Show[A] {
  def apply(): String
}

object NullaryShow {
  import scala.language.implicitConversions
  implicit def cqlTypeShow[A](show: CqlTypeShow[A]): NullaryShow[A] = show
  implicit def singletonStringShow[A<:String](show: SingletonStringShow[A]): NullaryShow[A] = show
}
