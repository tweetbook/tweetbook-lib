package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, UserBlock}

/*
 * テーブル設定
 */
case class UserBlockTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[UserBlock, P]
    with SlickColumnTypes[P] {
  import api._

  // DNS定義 ----------------------------------------------------------
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/tweetbook_core"),
    "slave" -> DataSourceName("ixias.db.mysql://slave/tweetbook_core")
  )

  // クエリー定義 -----------------------------------------------------
  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  // テーブル定義 -----------------------------------------------------
  class Table(tag: Tag) extends BasicTable(tag, "user_block") {

    /* @1 */ def id             = column[UserBlock.Id]  ("id",               O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def blockingUserId = column[User.Id]       ("blocking_user_id", O.UInt16)
    /* @3 */ def blockedUserId  = column[User.Id]       ("blocked_user_id",  O.UInt16)
    /* @4 */ def updatedAt      = column[LocalDateTime] ("updated_at",       O.TsCurrent)
    /* @5 */ def createdAt      = column[LocalDateTime] ("created_at",       O.Ts)

    // 全ての列を含むレコードとモデルのマッピング
    def * = (
      id.?, blockingUserId, blockedUserId, updatedAt, createdAt,
    ) <> (
      // Tuple => Model のマッピング
      (UserBlock.apply _).tupled,
      // Model => Tuple のマッピング
      (v: TableElementType) => UserBlock.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
