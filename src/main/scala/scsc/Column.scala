package scsc

sealed trait Column[A] {
  type Name <: String
  val name: Name
}

object Column {
  type Aux[A, N <: String] = Column[A] { type Name = N }

  def apply[A, N <: Singleton with String](
      implicit n: ValueOf[N]
  ): Column.Aux[A, N] = new Column[A] {
    type Name = N
    val name: Name = n.value
  }
}
