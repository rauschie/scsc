package scsc

/**
  * Trait representing a database column
  */
sealed trait Column[Name <: String, A]

object Column {

  def apply[Name <: Singleton with String, A]: Column[Name, A] =
    new Column[Name, A] {}
}
