package scsc

import scsc.ops.columnfamily.Columns
import shapeless.HList
import shapeless.ops.hlist.ToList

package object cql {
  protected val CR: String = System.lineSeparator()

  sealed trait Query[P <: HList, C <: HList, O <: HList] {
    def query(table: ColumnFamily[P, C, O]): String
  }

  //sealed trait SelectFrom[A, B, C] extends Query[A, B, C]

  //sealed trait DeleteFrom[A, B, C] extends Query[A, B, C]


  /*
  CREATE TABLE table_name(
  a ATYPE,
  b BTYPE,
  c CTYPE,
  PRIMARY KEY((a),b))
   */
  sealed trait Create[P <: HList, C <: HList, O <: HList] extends Query[P, C, O]

  /*
  INSERT INTO cycling.cyclist_name (id, lastname, firstname) VALUES (5b6962dd-3f90-4c93-8f61-eabfa4a803e2, 'VOS','Marianne')
   */
  sealed trait InsertInto[A <: HList, B <: HList, C <: HList] extends Query[A, B, C]

  object Create {
    def apply[P <: HList, C <: HList, O <: HList, PCO <: HList](implicit columns: Columns.Aux[P, C, O, PCO],
                                                                ev1: ToList[P, Column],
                                                                ev2: ToList[C, Column],
                                                                ev3: ToList[PCO, Column]): Create[P, C, O] = new Create[P, C, O] {
      def query(table: ColumnFamily[P, C, O]): String =
        columns(table).toList[Column]
                      .foldLeft(s"CREATE TABLE ${table.name} (" + CR)((str: String, col: Column) => str + s"${col.name}, ${col.cqlType}," + CR) +
          "PRIMARY KEY (" + table.partitioningColumns.toList
                                 .map(_.name)
                                 .mkString("(", ", ", "), ") +
          table.clusteringColumns.toList[Column]
               .map(_.name)
               .mkString(", ") + "))"
    }
  }

  object InsertInto {
    implicit def apply[P <: HList, C <: HList, O <: HList, PCO <: HList](implicit columns: Columns.Aux[P, C, O, PCO],
                                                                         ev: ToList[PCO, Column]): InsertInto[P, C, O] = new InsertInto[P, C, O] {
      def query(table: ColumnFamily[P, C, O]): String = {
        val columnNames = columns(table).toList[Column].map(_.name)
        s"INSERT INTO ${table.name}" +
          columnNames.mkString("(", ", ", ") ") +
          "VALUES " + columnNames.map(_ => "?").mkString("(", ", ", ")")
      }
    }
  }

}
