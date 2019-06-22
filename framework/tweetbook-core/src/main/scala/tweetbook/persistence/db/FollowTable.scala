package tweetbook.persistence.db

import java.time.LocalDateTime

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import tweetbook.model.{User, Follow}

case class FollowTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Follow, P]
    with SlickColumnTypes[P] {
  import api._

  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/tweetbook_core"),
    "slave" -> DataSourceName("ixias.db.mysql://slave/tweetbook_core")
  )

  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  class Table(tag: Tag) extends BasicTable(tag, "follow") {

    /* @1 */ def id         = column[Follow.Id]     ("id",          O.UInt16, O.PrimaryKey, O.AutoInc)
    /* @2 */ def followerId = column[User.Id]       ("follower_id", O.UInt16)
    /* @3 */ def followeeId = column[User.Id]       ("followee_id", O.UInt16)
    /* @4 */ def updatedAt  = column[LocalDateTime] ("updated_at",  O.TsCurrent)
    /* @5 */ def createdAt  = column[LocalDateTime] ("created_at",  O.Ts)

    // The * projection of the table
    def * = (
      id.?, followerId, followeeId, updatedAt, createdAt,
    ) <> (
      /* The bidirectional mappings : Tuple(table) => Model */
      (Follow.apply _).tupled,
      /* The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Follow.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
