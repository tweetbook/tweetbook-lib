package tweetbook.persistence.db

import slick.jdbc.JdbcProfile

trait SlickResourceProvider[P <: JdbcProfile] extends SlickColumnTypes[P] {
  implicit val driver: P

  object UserTable                 extends UserTable
  object FollowTable               extends FollowTable
  object FavoriteTable             extends FavoriteTable
  object TweetTable                extends TweetTable
  object UserPasswordTable         extends UserPasswordTable

  lazy val AllTables = Seq(
    UserTable,
    FollowTable,
    FavoriteTable,
    TweetTable,
    UserPasswordTable
  )
}

