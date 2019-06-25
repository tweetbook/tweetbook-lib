package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, Follow}

/*
 * テーブル設定
 */
case class FollowTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Follow, P]
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
  class Table(tag: Tag) extends BasicTable(tag, "follow") {

    /* @1 */ def id         = column[Follow.Id]     ("id",          O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def followerId = column[User.Id]       ("follower_id", O.UInt16)
    /* @3 */ def followeeId = column[User.Id]       ("followee_id", O.UInt16)
    /* @4 */ def updatedAt  = column[LocalDateTime] ("updated_at",  O.TsCurrent)
    /* @5 */ def createdAt  = column[LocalDateTime] ("created_at",  O.Ts)

    // 全ての列を含むレコードとモデルのマッピング
    def * = (
      id.?, followerId, followeeId, updatedAt, createdAt,
    ) <> (
      // Tuple => Model のマッピング
      (Follow.apply _).tupled,
      // Model => Tuple のマッピング
      (v: TableElementType) => Follow.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
