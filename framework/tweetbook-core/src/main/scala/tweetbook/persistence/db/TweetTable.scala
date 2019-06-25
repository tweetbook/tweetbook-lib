package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, Tweet}

/*
 * テーブル設定
 */
case class TweetTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Tweet, P]
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
  class Table(tag: Tag) extends BasicTable(tag, "tweet") {

    /* @1 */ def id        = column[Tweet.Id]         ("id",         O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def userId    = column[User.Id]          ("user_id",    O.UInt16)
    /* @3 */ def replyTo   = column[Option[Tweet.Id]] ("reply_to",   O.UInt16)
    /* @4 */ def content   = column[String]           ("content",    O.Utf8BinChar255)
    /* @5 */ def updatedAt = column[LocalDateTime]    ("updated_at", O.TsCurrent)
    /* @6 */ def createdAt = column[LocalDateTime]    ("created_at", O.Ts)

    // 全ての列を含むレコードとモデルのマッピング
    def * = (
      id.?, userId, replyTo, content, updatedAt, createdAt,
    ) <> (
      // Tuple => Model のマッピング
      (Tweet.apply _).tupled,
      // Model => Tuple のマッピング
      (v: TableElementType) => Tweet.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
