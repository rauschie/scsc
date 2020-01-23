package scsc

package object ops {

  import scsc.ops.hlist._
  import shapeless.HList
  sealed trait TypeMapping {
    type MappedTo
  }

  sealed trait AsColumns[L] {
    type UnderlyingTypes <: HList
    type Names <: HList
  }

  trait UnaryTypeMapping[A] extends TypeMapping

  trait BinaryTypeMapping[A, B] extends TypeMapping

  object AsColumns {

    type Aux[L, U <: HList, N <: HList] = AsColumns[L] {
      type UnderlyingTypes = U
      type Names = N
    }

    implicit def asColumns[L](
        implicit getNames: GetNames[L],
        getTypes: GetTypes[L]
    ): Aux[L, getTypes.MappedTo, getNames.MappedTo] = new AsColumns[L] {
      type UnderlyingTypes = getTypes.MappedTo
      type Names = getNames.MappedTo
    }
  }
}
