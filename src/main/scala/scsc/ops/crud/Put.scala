package scsc.ops.crud

import com.datastax.oss.driver.api.core.CqlSession
import shapeless.HList

import com.datastax.oss.driver.api.core.cql.SimpleStatement

sealed trait Put[K, O] {

  import com.datastax.oss.driver.api.core.cql.BoundStatement

  type Record <: HList
  val put: (Record, CqlSession) => BoundStatement

  def getQuery(tableName: String): String

  def prepare(tableName: String): SimpleStatement
}

object Put {

/*
  import scsc.ops.AsColumns
  import scsc.ops.hlist.{Extract, SetColumnValues}
  import shapeless.ops.hlist.Prepend

  type Aux[K, O, R] = Put[K, O] {
    type Record = R
  }

  implicit def put[K <: HList, O <: HList, C <: HList, R <: HList](
                                                                      implicit keyColumns: AsColumns[K],
                                                                      optionalColumns: AsColumns[O],
                                                                      ev: Prepend.Aux[K, O, C],
                                                                      ev1: C Extract R,
                                                                      ev2: SetColumnValues.Aux[C, R]
  ): Aux[K, O, R] = new Put[K, O] {

    import com.datastax.oss.driver.api.core.cql.BoundStatement

    type Record = R

    def getQuery(tableName: String): String = {
      val columns = keyColumns.names ::: optionalColumns.names
      s"INSERT INTO $tableName (${columns.mkString(", ")}) " +
        s"VALUES (${columns map (_ => "?") mkString ", "})"
    }

    def prepare(tableName: String): SimpleStatement =
      SimpleStatement.newInstance(getQuery(tableName))

    val put: (R, CqlSession) => BoundStatement =
      (record: Record, session: CqlSession) => {
        import scsc.syntax.BoundStatementOps
        val b: BoundStatement = ???
        b.setToRecord(record)
      }
  }
*/
}
