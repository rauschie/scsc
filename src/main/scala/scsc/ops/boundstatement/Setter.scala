package scsc.ops

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.cql.BoundStatement

trait Setter[A] {
  def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: A): BoundStatement
}

object Setter {

  implicit object setBoolean extends Setter[Boolean] {
    def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: Boolean): BoundStatement = boundStatement.setBoolean(columnId, v)
  }

  implicit object setDouble extends Setter[Double] {
    def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: Double): BoundStatement = boundStatement.setDouble(columnId, v)
  }

  implicit object setInt extends Setter[Int] {
    def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: Int): BoundStatement = boundStatement.setInt(columnId, v)
  }

  implicit object setLong extends Setter[Long] {
    def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: Long): BoundStatement = boundStatement.setLong(columnId, v)
  }

  implicit object setString extends Setter[String] {
    def setValueTo(boundStatement: BoundStatement, columnId: CqlIdentifier, v: String): BoundStatement = boundStatement.setString(columnId, v)
  }

  implicit object setFloat extends Setter[Float] {
    def setValueTo(record: BoundStatement, columnId: CqlIdentifier, v: Float): BoundStatement = record.setFloat(columnId, v)
  }

}