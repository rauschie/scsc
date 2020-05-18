package scsc

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

trait Transaction[A] {
  self =>
  def run(implicit ec: ExecutionContext): Future[A]
  def map[B](f: A => B): Transaction[B] = new Transaction[B] {
    def run(implicit ec: ExecutionContext) = self.run map f
  }
  def flatMap[B](f: A => Transaction[B]): Transaction[B] = new Transaction[B] {
    def run(implicit ec: ExecutionContext): Future[B] =
      self.run flatMap (f(_).run)
  }
}
