package scsc.ops.crud

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.PreparedStatement
import shapeless.HList

sealed trait Put[K, O] {

  type Record <: HList

  def getQuery(tableName: String): String

  def prepare(tableName: String, session: CqlSession): PreparedStatement
}

object Put {

  import scsc.ops.Columns
  import scsc.ops.hlist.{Extract, SetColumnValues}
  import shapeless.ops.hlist.Prepend

  type Aux[K, O, R] = Put[K, O] {
    type Record = R
  }

  implicit def put[K <: HList, O <: HList, C <: HList, R <: HList](implicit keyColumns: Columns[K],
                                                                   optionalColumns: Columns[O],
                                                                   ev: Prepend.Aux[K, O, C],
                                                                   ev1: C Extract R,
                                                                   ev2: SetColumnValues.Aux[C, R]): Aux[K, O, R] = new Put[K, O] {
    type Record = R

    def getQuery(tableName: String): String = {
      val columns = keyColumns.names ::: optionalColumns.names
      s"INSERT INTO $tableName (${columns.mkString(", ")}) " +
        s"VALUES (${columns map (_ => "?") mkString ", "})"
    }

    def prepare(tableName: String, session: CqlSession): PreparedStatement = session.prepare(getQuery(tableName))
  }
}
