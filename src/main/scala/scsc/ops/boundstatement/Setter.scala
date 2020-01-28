package scsc.ops.boundstatement

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.cql.BoundStatement

sealed trait Setter[A] {
  //todo make this use a Builder
  def setValueTo(
      boundStatement: BoundStatement,
      columnId: CqlIdentifier,
      v: A
  ): BoundStatement
}

object Setter {

  implicit object setBoolean extends Setter[Boolean] {
    def setValueTo(
        boundStatement: BoundStatement,
        columnId: CqlIdentifier,
        v: Boolean
    ): BoundStatement = boundStatement.setBoolean(columnId, v)
  }

  implicit object setDouble extends Setter[Double] {
    def setValueTo(
        boundStatement: BoundStatement,
        columnId: CqlIdentifier,
        v: Double
    ): BoundStatement = boundStatement.setDouble(columnId, v)
  }

  implicit object setInt extends Setter[Int] {
    def setValueTo(
        boundStatement: BoundStatement,
        columnId: CqlIdentifier,
        v: Int
    ): BoundStatement = boundStatement.setInt(columnId, v)
  }

  implicit object setLong extends Setter[Long] {
    def setValueTo(
        boundStatement: BoundStatement,
        columnId: CqlIdentifier,
        v: Long
    ): BoundStatement = boundStatement.setLong(columnId, v)
  }

  implicit object setString extends Setter[String] {
    def setValueTo(
        boundStatement: BoundStatement,
        columnId: CqlIdentifier,
        v: String
    ): BoundStatement = boundStatement.setString(columnId, v)
  }

  implicit object setFloat extends Setter[Float] {
    def setValueTo(
        record: BoundStatement,
        columnId: CqlIdentifier,
        v: Float
    ): BoundStatement = record.setFloat(columnId, v)
  }

  implicit def setOption[A](implicit setter: Setter[A]): Setter[Option[A]] =
    new Setter[Option[A]] {
      def setValueTo(
          boundStatement: BoundStatement,
          columnId: CqlIdentifier,
          v: Option[A]
      ): BoundStatement = v match {
        case Some(value) => setter.setValueTo(boundStatement, columnId, value)
        case None        => boundStatement
      }
    }
}
