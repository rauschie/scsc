package scsc.ops.crud

import com.datastax.oss.driver.api.core.cql.SimpleStatement

sealed trait Create[P, C, O] {

  import scsc.ColumnFamily

  /*
    CREATE TABLE cycling.cyclist_id (
      lastname text,
      firstname text,
      age int,
      id UUID,
      PRIMARY KEY ((lastname, firstname), age) );
  */
  def apply(keySpaceName: String, tableName: String): SimpleStatement
}

object Create {

  import scsc.ops.CqlColumns

  implicit def create[P, C, O](implicit part: CqlColumns[P],
                               clust: CqlColumns[C],
                               opt: CqlColumns[O]): Create[P, C, O] = new Create[P, C, O] {

    def apply(keySpaceName: String, tableName: String): SimpleStatement = {
      import com.datastax.oss.driver.api.core.cql.SimpleStatement
      val partitioningNames = part.names
      val clusteringNames = clust.names
      val names = partitioningNames ::: clusteringNames ::: opt.names
      val typeNames = part.getCqlTypeNames ::: clust.getCqlTypeNames ::: opt.getCqlTypeNames
      SimpleStatement.newInstance(
        s"CREATE TABLE $keySpaceName.$tableName (" + CR +
          names.zip(typeNames)
               .map { case (colName, colType) => s"$colName $colType," }
               .mkString(CR) +
          CR + s"PRIMARY KEY ((${partitioningNames.mkString(", ")}), ${clusteringNames.mkString(", ")}))"
        )
    }
  }
}
