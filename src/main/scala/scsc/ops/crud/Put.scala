package scsc.ops.crud

import shapeless.HList
import scsc.ops.hlist.{GetNames, ToStrings}
import shapeless.BasisConstraint

sealed trait Put[Kl, Cl, Nl] {

  def getQuery(keySpaceName: String, tableName: String): String
}

object Put extends {

  implicit def put[Kl <: HList, Cl, Kn <: HList, Cn <: HList, Nl <: HList](
      implicit keyColumnNames: GetNames.Aux[Kl, Kn],
      columnNames: GetNames.Aux[Cl, Cn],
      ev: BasisConstraint[Kn, Nl],
      names: ToStrings[Nl]
  ): Put[Kl, Cl, Nl] = new Put[Kl, Cl, Nl] {

    type Names = Nl

    def getQuery(keySpaceName: String, tableName: String): String = {
      s"INSERT INTO $tableName (${names().mkString(", ")}) " +
        s"VALUES (${names() map (_ => "?") mkString ", "})"
    }
  }
}
