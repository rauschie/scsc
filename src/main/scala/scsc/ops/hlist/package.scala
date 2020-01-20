package scsc.ops

import shapeless.HList

package object hlist {
  type Extract[L, U <: HList] = GetTypes.Aux[L, U]
  type ToKey[L, O <: HList] = ToKeyColumns.Aux[L, O]
  type ToOptional[L, O <: HList] = ToOptionalColumns.Aux[L, O]
}