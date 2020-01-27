package scsc.ops.crud

import shapeless.HList

sealed trait Put[Kl, Cl] {
  type Names <: HList

  def getQuery(keySpaceName: String, tableName: String): String
}

object Put {
//todo make this pass tests
  import scsc.ops.hlist.{GetNames, ToStrings}
  import shapeless.BasisConstraint
  import shapeless.ops.hlist.Prepend

  type Aux[Kl, Cl, Nl] = Put[Kl, Cl] {
    type Names = Nl
  }

  implicit def put[Kl <: HList, Cl, Kn<:HList, Cn<:HList, Nl <: HList](
      implicit keyNames: GetNames.Aux[Kl, Kn],
      columnNames: GetNames.Aux[Cl, Cn],
      ev: BasisConstraint[Kn, Nl],
      names: ToStrings[Nl]
  ): Aux[Kl, Cl, Nl] = new Put[Kl, Cl] {

    type Names = Nl

    def getQuery(keySpaceName: String, tableName: String): String = {
      s"INSERT INTO $tableName (${names().mkString(", ")}) " +
        s"VALUES (${names() map (_ => "?") mkString ", "})"
    }
  }

  implicit def put[Kl <: HList, Cl <: HList, Nl <: HList](
      implicit keyColumnNames: GetNames[Kl],
      columnNames: GetNames.Aux[Cl, Nl],
      names: ToStrings[Nl]
  ): Put[Kl, Cl] = new Put[Kl, Cl] {
    type Names = Nl

    def getQuery(keySpaceName: String, tableName: String): String = ???
  }
}
