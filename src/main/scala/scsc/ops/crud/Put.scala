package scsc.ops.crud

import com.datastax.oss.driver.api.core.CqlSession
import shapeless.HList

import com.datastax.oss.driver.api.core.cql.SimpleStatement

sealed trait Put[Kl, Cl] {
  type Names <: HList

  import com.datastax.oss.driver.api.core.cql.BoundStatement

  def getQuery(keySpaceName: String, tableName: String): String
}

object Put {

  import scsc.ops.hlist.{AsColumns, Extract, GetNames, SetColumnValues}
  import shapeless.ops.hlist.Prepend
  import shapeless.BasisConstraint

  type Aux[Kl, Cl, Nl] = Put[Kl, Cl] {
    type Names = Nl
  }

  implicit def put[Kl: GetNames, Kn, Cl, Cn, Nl](
      implicit keyNames: GetNames.Aux[Kl, Kn],
      ev: BasisConstraint[Kn, Nl]
  ): Aux[Kl, Cl, Nl] = new Put[Kl, Cl] {

    import com.datastax.oss.driver.api.core.cql.BoundStatement

    type Names = Nl

    def getQuery(tableName: String): String = {
      val columns = keyColumns.names ::: optionalColumns.names
      columNames
      s"INSERT INTO $tableName (${columns.mkString(", ")}) " +
        s"VALUES (${columns map (_ => "?") mkString ", "})"
    }
  }
}
