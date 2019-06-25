package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.User

/*
 * テーブル設定
 */
case class UserTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[User, P]
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
  class Table(tag: Tag) extends BasicTable(tag, "user") {

    /* @1 */ def id          = column[User.Id]       ("id",          O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def name        = column[String]        ("name",        O.Utf8Char255)
    /* @3 */ def email       = column[String]        ("email",       O.Utf8Char255)
    /* @4 */ def description = column[Option[String]]("description", O.Utf8Char255)
    /* @5 */ def iconUrl     = column[Option[String]]("icon_url",    O.Utf8Char255)
    /* @6 */ def updatedAt   = column[LocalDateTime] ("updated_at",  O.TsCurrent)
    /* @7 */ def createdAt   = column[LocalDateTime] ("created_at",  O.Ts)

    // 全ての列を含むレコードとモデルのマッピング
    def * = (
      id.?, name, email, description, iconUrl, updatedAt, createdAt
    ) <> (
      // Tuple => Model のマッピング
      (User.apply _).tupled,
      // Model => Tuple のマッピング
      (v: TableElementType) => User.unapply(v).map(_.copy(
        _6 = LocalDateTime.now
      ))
    )
  }
}
