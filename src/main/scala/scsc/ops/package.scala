package scsc

package object ops {

  import scsc.ops.hlist._
  import shapeless.HList

  sealed trait AsColumns[L] {
    type UnderlyingTypes <: HList
    type Names <: HList
  }

  object AsColumns {

    type Aux[L, U <: HList, N <: HList] = AsColumns[L] {
      type UnderlyingTypes = U
      type Names = N
    }

    implicit def asColumns[L](
        implicit getNames: GetNames[L],
        getTypes: GetTypes[L]
    ): Aux[L, getTypes.Out, getNames.Out] = new AsColumns[L] {
      type UnderlyingTypes = getTypes.Out
      type Names = getNames.Out
    }
  }
}
