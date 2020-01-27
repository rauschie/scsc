package scsc.ops.show

trait SingletonStringShow[S <: String] extends NullaryShow[S]

object SingletonStringShow {

  implicit def singletonStringShow[N <: Singleton with String](
      implicit name: ValueOf[N]
  ): SingletonStringShow[N] = new SingletonStringShow[N] {
    def apply(): String = name.value

    def show(t: N): String = apply()
  }
}
