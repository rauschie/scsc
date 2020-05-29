package scsc.ops.crud

import shapeless.HList
import scsc.ops.hlist.{GetNames, ToStrings}
import shapeless.BasisConstraint
import scala.annotation.nowarn

sealed trait Put[KeyColumns, Columns, ColumnNames] {

  val query: (String, String) => String
}

object Put {
//todo try making implicit resolution work with only 2 types
  @nowarn("cat=unused-params")
  implicit def put[Kl <: HList, Cl, Kn <: HList, Cn <: HList, Names <: HList](
      implicit keyColumnNames: GetNames.Aux[Kl, Kn],
      columnNames: GetNames.Aux[Cl, Cn],
      ev: BasisConstraint[Kn, Names],
      names: ToStrings[Names]
  ): Put[Kl, Cl, Names] = new Put[Kl, Cl, Names] {
    val query = (keySpaceName, tableName) =>
      s"INSERT INTO $tableName (${names().mkString(", ")}) " +
        s"VALUES (${(names() map (_ => "?")).mkString(", ")})"
  }
}
