package scsc.ops

import java.net.InetAddress
import java.nio.ByteBuffer
import java.time.LocalDate
import java.util.UUID

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.cql.Row

trait Get[A] {
  def apply(row: Row, cqlIdentifier: CqlIdentifier): A
}

object Get {

  implicit object getBoolean extends Get[Boolean] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): Boolean = row.getBoolean(cqlIdentifier)
  }

  implicit object getByteBuffer extends Get[ByteBuffer] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): ByteBuffer = row.getByteBuffer(cqlIdentifier)
  }

  implicit object getDate extends Get[LocalDate] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): LocalDate = row.getLocalDate(cqlIdentifier)
  }

  implicit object getBigDecimal extends Get[BigDecimal] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): BigDecimal = row.getBigDecimal(cqlIdentifier)
  }

  implicit object getBigInt extends Get[BigInt] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): BigInt = row.getBigInteger(cqlIdentifier)
  }

  implicit object getDouble extends Get[Double] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): Double = row.getDouble(cqlIdentifier)
  }

  implicit object getFloat extends Get[Float] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): Float = row.getFloat(cqlIdentifier)
  }

  implicit object getInt extends Get[Int] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): Int = row.getInt(cqlIdentifier)
  }

  implicit object getInetAddress extends Get[InetAddress] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): InetAddress = row.getInetAddress(cqlIdentifier)
  }

  implicit object getUUID extends Get[UUID] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): UUID = row.getUuid(cqlIdentifier)
  }

  implicit object getString extends Get[String] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): String = row.getString(cqlIdentifier)
  }

  implicit object getLong extends Get[Long] {
    def apply(row: Row, cqlIdentifier: CqlIdentifier): Long = row.getLong(cqlIdentifier)
  }

}