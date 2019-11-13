package scsc

sealed trait Column {
  type Underlying
  type Name <: String
  val name: Name
  val cqlType: CqlType
}

object Column {
  type Aux[U, N <: String] = Column {
    type Name = N
    type Underlying = U}
  type Named[N <: String] = Column {type Name = N}
  type BackedBy[U] = Column {type Underlying = U}

  def apply[N <: Singleton with String](cqlT: CqlType, n: N): Column.Aux[Option[cqlT.JavaType], N] = new Column {
    type Underlying = Option[cqlT.JavaType]
    type Name = N
    val name: Name = n
    val cqlType: CqlType= cqlT
  }

  def key[N <: Singleton with String](cqlT: CqlType, n: N): Column.Aux[cqlT.JavaType, N] = new Column {
    type Underlying = cqlT.JavaType
    type Name = N
    val name: Name = n
    val cqlType: CqlType = cqlT
  }

}



