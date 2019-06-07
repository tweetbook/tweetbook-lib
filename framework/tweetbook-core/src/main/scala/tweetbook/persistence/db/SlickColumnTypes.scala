package tweetbook.persistence.db

import slick.jdbc.JdbcProfile
import tweetbook.model._

trait SlickColumnTypes[P <: JdbcProfile] {
  implicit val driver: P
  import driver.api._

  implicit val mappingOfUserId = MappedColumnType.base[User.Id, Long](id => id, User.Id(_))
  implicit val mappingOfFollowId = MappedColumnType.base[Follow.Id, Long](id => id, Follow.Id(_))
  implicit val mappingOfFavoriteId = MappedColumnType.base[Favorite.Id, Long](id => id, Favorite.Id(_))
  implicit val mappingOfTweetId = MappedColumnType.base[Tweet.Id, Long](id => id, Tweet.Id(_))
}
