package scsc.ops.boundstatement

import com.datastax.oss.driver.api.core.cql.BoundStatement
import shapeless.{::, HList, HNil}

trait SetColumnValues[L <: HList] {
  type Record <: HList

  def apply(boundStatement: BoundStatement, record: Record): BoundStatement
}

object SetColumnValues {

  import scsc.Column

  type Aux[L <: HList, R <: HList] = SetColumnValues[L] {type Record = R}

  implicit def setHCons[N <: Singleton with String,
    V: Setter,
    T <: HList](implicit name: ValueOf[N],
                setTailValues: SetColumnValues[T]): Aux[Column.Aux[V, N] :: T, V :: setTailValues.Record] = new SetColumnValues[Column.Aux[V, N] :: T] {

    import scsc.syntax.BoundStatementOps

    type Record = V :: setTailValues.Record

    override def apply(boundStatement: BoundStatement, record: Record): BoundStatement = setTailValues(boundStatement.set(name.value, record.head), record.tail)
  }

  implicit object setHNil extends SetColumnValues[HNil] {
    type Record = HNil

    def apply(boundStatement: BoundStatement, record: Record): BoundStatement = boundStatement
  }

}