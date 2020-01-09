package scsc.ops

package object crud {

  import shapeless.Poly1

   val CR: String = System.lineSeparator()

  object columnNames extends Poly1 {

    import scsc.ops.cqltype.GetCqlTypeName
    import scsc.{Column, CqlType}

    implicit def hConsCase[A, C <: CqlType](implicit ev: A <:< Column[C],
                                            nameOfType: GetCqlTypeName[C]): Case.Aux[A, String] = at(column =>
                                                                                               s"${column.name}, ${nameOfType()}" + CR)
  }

  /*
    object InsertInto {
      implicit def apply[P <: HList, C <: HList, O <: HList, PCO <: HList](implicit columns: Columns.Aux[P, C, O, PCO],
                                                                           ev: ToList[PCO, Column]): InsertInto[P, C, O] = new InsertInto[P, C, O] {
        def query(table: ColumnFamily[P, C, O]): String = {
          val columnNames = columns(table).toList[Column] map(_.name)
          s"INSERT INTO ${table.name}" +
            columnNames.mkString("(", ", ", ") ") +
            "VALUES " + columnNames.map(_ => "?").mkString("(", ", ", ")")
        }
      }
    }
  */
}
