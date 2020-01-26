package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{HList, ::, HNil}

trait SubsetByNames[L, N] extends BinaryTypeMapping[L, N] {
  type MappedTo <: HList
}

object SubsetByNames extends LowPrioritySubsetByNames {

  type Aux[L, N, M <: HList] = SubsetByNames[L, N] {
    type MappedTo = M
  }

  implicit def matching[L, N <: Singleton with String, T <: HList](
      implicit ev: FindByName[L, N],
      findNames: SubsetByNames[L, T]
  ): Aux[L, N :: T, ev.MappedTo :: findNames.MappedTo] = new SubsetByNames[L, N :: T] {
    type MappedTo = ev.MappedTo :: findNames.MappedTo
  }
  implicit def retainHNil[L]: Aux[L, HNil, HNil] = new SubsetByNames[L, HNil] {
    type MappedTo = HNil
  }
}

trait LowPrioritySubsetByNames {

  import scsc.ops.hlist.SubsetByNames.Aux

  implicit def skip[L, N <: Singleton with String, T <: HList](
      implicit findNames: SubsetByNames[L, T]
  ): Aux[L, N :: T, findNames.MappedTo] = new SubsetByNames[L, N :: T] {
    type MappedTo = findNames.MappedTo
  }
}
