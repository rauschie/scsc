package scsc.ops

import com.datastax.oss.driver.api.core.cql.BoundStatement

trait Set[A] {
  def apply(boundStatement: BoundStatement, columnName: String, v: A): BoundStatement
}

object Set {

  implicit object setBoolean extends Set[Boolean] {
    def apply(boundStatement: BoundStatement, columnName: String, v: Boolean): BoundStatement = boundStatement.setBoolean(columnName, v)
  }

  implicit object setDouble extends Set[Double] {
    def apply(boundStatement: BoundStatement, columnName: String, v: Double): BoundStatement = boundStatement.setDouble(columnName, v)
  }

  implicit object setInt extends Set[Int] {
    def apply(boundStatement: BoundStatement, columnName: String, v: Int): BoundStatement = boundStatement.setInt(columnName, v)
  }

  implicit object setLong extends Set[Long] {
    def apply(boundStatement: BoundStatement, columnName: String, v: Long): BoundStatement = boundStatement.setLong(columnName, v)
  }

  implicit object setString extends Set[String] {
    def apply(boundStatement: BoundStatement, columnName: String, v: String): BoundStatement = boundStatement.setString(columnName, v)
  }

  implicit object setFloat extends Set[Float] {
    def apply(boundStatement: BoundStatement, columnName: String, v: Float): BoundStatement = boundStatement.setFloat(columnName, v)
  }

}
