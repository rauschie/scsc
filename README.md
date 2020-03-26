# <img src="https://render.githubusercontent.com/render/math?math=(sc)^2">
### Simple Cassandra-Scala Connectivity
#### A functional wrapper around [DataStax Java Driver for Apache Cassandra](https://github.com/datastax/java-driver)

#### Typesafe CRUD transactions checked at compile time:
```scala
import scsc.{Cassandra, ColumnFamily, Transaction}
import scsc.CqlDataType._
import shapeless.{::, HNil}


val transaction: Transaction[Unit] = Cassandra
  .getKeySpace("cycling")
  .flatMap(schema =>
      schema
        .get("cyclist_name",
             ColumnFamily(Long("id") :: String("firstname") :: String("lastname") :: HNil)
             )
        .map(table =>
               table.put(1 :: "Ratto" :: "Risella" :: HNil)
             )
  )

lazy val run: Future[Unit] = transaction.future
```
