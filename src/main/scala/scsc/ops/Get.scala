package scsc.ops

import java.net.InetAddress
import java.nio.ByteBuffer
import java.time.LocalDate
import java.util.UUID

import com.datastax.oss.driver.api.core.cql.Row

trait Get[A] {
  def apply(row: Row, columnName: String): A
}

object Get {

  implicit object getBoolean extends Get[Boolean] {
    def apply(row: Row, columnName: String): Boolean = row.getBoolean(columnName)
  }

  implicit object getByteBuffer extends Get[ByteBuffer] {
    def apply(row: Row, columnName: String): ByteBuffer = row.getByteBuffer(columnName)
  }

  implicit object getDate extends Get[LocalDate] {
    def apply(row: Row, columnName: String): LocalDate = row.getLocalDate(columnName)
  }

  implicit object getBigDecimal extends Get[BigDecimal] {
    def apply(row: Row, columnName: String): BigDecimal = row.getBigDecimal(columnName)
  }

  implicit object getBigInt extends Get[BigInt] {
    def apply(row: Row, columnName: String): BigInt = row.getBigInteger(columnName)
  }

  implicit object getDouble extends Get[Double] {
    def apply(row: Row, columnName: String): Double = row.getDouble(columnName)
  }

  implicit object getFloat extends Get[Float] {
    def apply(row: Row, columnName: String): Float = row.getFloat(columnName)
  }

  implicit object getInt extends Get[Int] {
    def apply(row: Row, columnName: String): Int = row.getInt(columnName)
  }

  implicit object getInetAddress extends Get[InetAddress] {
    def apply(row: Row, columnName: String): InetAddress = row.getInetAddress(columnName)
  }

  implicit object getUUID extends Get[UUID] {
    def apply(row: Row, columnName: String): UUID = row.getUuid(columnName)
  }

  implicit object getString extends Get[String] {
    def apply(row: Row, columnName: String): String = row.getString(columnName)
  }

  implicit object getLong extends Get[Long] {
    def apply(row: Row, columnName: String): Long = row.getLong(columnName)
  }

}
