package scsc


trait ColumnBlueprint {
  type JavaType
  type Name <: String
  val name: Name
  val cqlType: CqlType.Aux[JavaType]
}

object ColumnBlueprint {
  type Aux[J, N <: String] = ColumnBlueprint {
    type JavaType = J
    type Name = N
  }

  def apply[J, N <: Singleton with String](cqlT: CqlType.Aux[J], n: N): ColumnBlueprint.Aux[J, N] = new ColumnBlueprint {
    type JavaType = J
    type Name = N
    val name: Name = n
    val cqlType: CqlType.Aux[JavaType] = cqlT
  }
}

