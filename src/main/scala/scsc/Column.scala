package scsc

sealed trait Column[N <: String, A]

object Column {

  def apply[N <: Singleton with String, A]: Column[N, A] = new Column[N, A] {}
}
