package scsc

import shapeless.{HList, HNil}

import com.datastax.oss.driver.api.core.CqlSession

import scsc.ops.hlist.SetColumnValues

sealed trait ColumnFamily {

  type Key <: HList
  type Record <: HList
  val name: String

  def create(keySpaceName: String)(implicit session: CqlSession): Unit

}

object ColumnFamily {

  import scsc.ops.Context
  import syntax.BoundStatementOps
  type Aux[K <: HList, R <: HList] = ColumnFamily {
    type Key = K
    type Record = R
  }

  def apply[P0 <: HList](n: String, p0: P0)(implicit ctx: Context[P0, HNil, HNil]): Aux[ctx.Key, ctx.Record] = new ColumnFamily {
    type Key = ctx.Key
    type Record = ctx.Record
    val name: String = n

    def create(keySpaceName: String)(implicit session: CqlSession): Unit = session.execute(ctx.create(keySpaceName, name))

  }

  def apply[P0 <: HList, O0 <: HList](n: String,
                                      partitioningCols: P0,
                                      optionalCols: O0)(implicit ctx: Context[P0, HNil, O0]): Aux[ctx.Key, ctx.Record] = new ColumnFamily {

    type Key = ctx.Key
    type Record = ctx.Record
    val name: String = n

    def create(keySpaceName: String)(implicit session: CqlSession): Unit = session.execute(ctx.create(keySpaceName, name))
  }

  def apply[P0 <: HList, C0 <: HList, O0 <: HList](n: String, keyCols: (P0, C0))(implicit ctx: Context[P0, C0, HNil]): Aux[ctx.Key, ctx.Record] = new ColumnFamily {
    type Key = ctx.Key
    type Record = ctx.Record
    val name: String = n

    def create(keySpaceName: String)(implicit session: CqlSession): Unit = session.execute(ctx.create(keySpaceName, name))
  }

  def apply[P0 <: HList, C0 <: HList, O0 <: HList](n: String,
                                                   keyCols: (P0, C0),
                                                   optionalCols: O0)(implicit ctx: Context[P0, C0, O0]): Aux[ctx.Key, ctx.Record] = new ColumnFamily {
    type Key = ctx.Key
    type Record = ctx.Record
    val name: String = n

    def create(keySpaceName: String)(implicit session: CqlSession): Unit = session.execute(ctx.create(keySpaceName, name))
  }
}