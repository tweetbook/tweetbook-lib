package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, Tweet, Favorite}

/*
 * テーブル設定
 */
case class FavoriteTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Favorite, P]
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
  class Table(tag: Tag) extends BasicTable(tag, "favorite") {

    /* @1 */ def id          = column[Favorite.Id]   ("id",         O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def tweetId     = column[Tweet.Id]      ("tweet_id",   O.UInt16)
    /* @3 */ def userId      = column[User.Id]       ("user_id",    O.UInt16)
    /* @4 */ def updatedAt   = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @5 */ def createdAt   = column[LocalDateTime] ("created_at", O.Ts)

    // 全ての列を含むレコードとモデルのマッピング
    def * = (
      id.?, tweetId, userId, updatedAt, createdAt
    ) <> (
      // Tuple => Model のマッピング
      (Favorite.apply _).tupled,
      // Model => Tuple のマッピング
      (v: TableElementType) => Favorite.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
