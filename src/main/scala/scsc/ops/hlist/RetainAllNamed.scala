package scsc.ops.hlist

import scsc.ops.BinaryTypeMapping
import shapeless.{HList, ::, HNil}

trait RetainAllNamed[L, N] extends BinaryTypeMapping[L, N] {
  type MappedTo <: HList
}

object RetainAllNamed extends LowPriorityRetainAllNamed {

  import shapeless.the

  type Aux[L, N, M <: HList] = RetainAllNamed[L, N] {
    type MappedTo = M
  }
  implicit def matching[L, N <: Singleton with String, T <: HList](
      implicit ev: FindByName[L, N],
      findNames: RetainAllNamed[L, T]
  ): Aux[L, N :: T, ev.MappedTo :: findNames.MappedTo] = new RetainAllNamed[L, N :: T] {
    type MappedTo = ev.MappedTo :: findNames.MappedTo
  }
  implicit def retainHNil[L]: Aux[L, HNil, HNil] = new RetainAllNamed[L, HNil] {
    type MappedTo = HNil
  }
}

trait LowPriorityRetainAllNamed {

  import scsc.ops.hlist.RetainAllNamed.Aux

  implicit def skip[L, N <: Singleton with String, T <: HList](
      implicit findNames: RetainAllNamed[L, T]
  ): Aux[L, N :: T, findNames.MappedTo] = new RetainAllNamed[L, N :: T] {
    type MappedTo = findNames.MappedTo
  }
}
