package scsc.poly

import shapeless.Poly1

object toStrings extends Poly1 {

  import cats.Show
  import cats.implicits.toShow
  implicit def defaultCase[A: Show]: Case.Aux[A, String] = at(a => a.show)
}
