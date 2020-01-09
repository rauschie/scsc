package scsc

package object ops {

  import scsc.ops.hlist._
  import shapeless.HList

  sealed trait Columns[L] {
    type Underlying <: HList

    def names: List[String]
  }

  sealed trait CqlColumns[L] extends Columns[L] {

    def getCqlTypeNames: List[String]
  }

  object Columns {
    type Aux[L, U <: HList] = Columns[L] { type Underlying = U }

    def apply[A](implicit columns: Columns[A]): Columns[A] = columns

    implicit def columns[L](
        implicit getNames: GetNames[L],
        ev: Underlying[L]
    ): Aux[L, ev.Out] = new Columns[L] {
      type Underlying = ev.Out
      lazy val names: List[String] = getNames()
    }
  }

  object CqlColumns {

    implicit def hConsCqlColumns[L, K <: HList, O <: HList](
        implicit ev: Columns[L],
        getCTNs: GetCqlTypeNames[L],
        evK: Columns[K],
        evO: Columns[O]
    ): CqlColumns[L] = new CqlColumns[L] {

      def getCqlTypeNames: List[String] = getCTNs()

      type Underlying = ev.Underlying

      def names: List[String] = ev.names
    }
  }

}
