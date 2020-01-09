package scsc.crud

import shapeless.HList

import com.datastax.oss.driver.api.core.cql.SimpleStatement

sealed trait Create[P <: HList, C <: HList, O <: HList] {

  import scsc.ColumnFamily

  /*
    CREATE TABLE cycling.cyclist_id (
      lastname text,
      firstname text,
      age int,
      id UUID,
      PRIMARY KEY ((lastname, firstname), age) );
  */
  def apply(keySpaceName: String, table: ColumnFamily[_, _, _]): SimpleStatement
}

object Create {

  import scsc.ops.hlist.{AsColumns, GetCqlTypeNames}

  implicit def create[P <: HList : GetCqlTypeNames,
    C <: HList : GetCqlTypeNames,
    O <: HList : GetCqlTypeNames](implicit part: AsColumns[P],
                                  clust: AsColumns[C],
                                  opt: AsColumns[O]): Create[P, C, O] = new Create[P, C, O] {

    import scsc.ColumnFamily

    def apply(keySpaceName: String, table: ColumnFamily[_, _, _]): SimpleStatement = {
      import com.datastax.oss.driver.api.core.cql.SimpleStatement
      val partitioningNames = part.names
      val clusteringNames = clust.names
      val names = partitioningNames ::: clusteringNames ::: opt.names
      val typeNames = part.getCqlTypeNames ::: clust.getCqlTypeNames ::: opt.getCqlTypeNames
      SimpleStatement.newInstance(
        s"CREATE TABLE $keySpaceName.${table.name} (" + CR +
          names.zip(typeNames)
               .map { case (colName, colType) => s"$colName $colType," }
               .mkString(CR) +
          CR + s"PRIMARY KEY ((${partitioningNames.mkString(", ")}), ${clusteringNames.mkString(", ")}))"
        )
    }
  }
}
