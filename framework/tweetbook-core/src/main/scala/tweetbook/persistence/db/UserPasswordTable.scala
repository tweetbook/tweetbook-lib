package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, UserPassword}

/*
 * テーブル設定
 */
case class UserPasswordTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[UserPassword, P]
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

    /* @1 */ def id        = column[User.Id]          ("id",         O.UInt16, O.PrimaryKey)
    /* @2 */ def hashed   = column[String]            ("hashed",     O.AsciiChar255)
    /* @3 */ def salt   = column[String]              ("content",    O.AsciiChar32)
    /* @4 */ def updatedAt = column[LocalDateTime]    ("updated_at", O.TsCurrent)
    /* @5 */ def createdAt = column[LocalDateTime]    ("created_at", O.Ts)

    // The * projection of the table
    def * = (
      id.?, hashed, salt, updatedAt, createdAt,
    ) <> (
      /* The bidirectional mappings : Tuple(table) => Model */
      (UserPassword.apply _).tupled,
      /* The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => UserPassword.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
