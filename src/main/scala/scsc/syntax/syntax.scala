package scsc

import shapeless.HList

import scala.language.implicitConversions

package object syntax {

  import com.datastax.oss.driver.api.core.cql.BoundStatement

  implicit class BoundStatementOps(val boundStatement: BoundStatement) extends AnyVal {

    import com.datastax.oss.driver.api.core.CqlIdentifier
    import scsc.ops.hlist.SetColumnValues.Aux
    import scsc.ops.boundstatement.Setter

    def setToRecord[L <: HList, R <: HList](record: R)(
        implicit setToRecord: L Aux R
    ): BoundStatement = setToRecord(boundStatement, record)

    def set[N <: Singleton with String, A](name: String, v: A)(
        implicit setter: Setter[A]
    ): BoundStatement =
      setter.setValueTo(boundStatement, CqlIdentifier.fromCql(name), v)
  }



}
