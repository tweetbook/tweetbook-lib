package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, Tweet}

case class TweetTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Tweet, P]
    with SlickColumnTypes[P] {
  import api._

  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/tweetbook_core"),
    "slave" -> DataSourceName("ixias.db.mysql://slave/tweetbook_core")
  )

  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  class Table(tag: Tag) extends BasicTable(tag, "tweet") {

    /* @1 */ def id        = column[Tweet.Id]         ("id",         O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def authorId  = column[User.Id]          ("author_id",  O.UInt16)
    /* @3 */ def replyTo   = column[Option[Tweet.Id]] ("reply_to",   O.UInt16)
    /* @4 */ def content   = column[String]           ("content",    O.Utf8BinChar255)
    /* @5 */ def updatedAt = column[LocalDateTime]    ("updated_at", O.TsCurrent)
    /* @6 */ def createdAt = column[LocalDateTime]    ("created_at", O.Ts)

    // The * projection of the table
    def * = (
      id.?, authorId, replyTo, content, updatedAt, createdAt,
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Tweet.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Tweet.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
