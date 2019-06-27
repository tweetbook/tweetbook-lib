package tweetbook.persistence.db

import slick.jdbc.JdbcProfile
import tweetbook.model._

/*
 * 任意の型とSlickがサポートする型との変換定義
 */
trait SlickColumnTypes[P <: JdbcProfile] {
  implicit val driver: P
  import driver.api._

  // 各モデルのID型とSlickがサポートする型との変換定義 -------------------------------------
  implicit val mappingOfUserId = MappedColumnType.base[User.Id, Long](id => id, User.Id(_))
  implicit val mappingOfFollowId = MappedColumnType.base[Follow.Id, Long](id => id, Follow.Id(_))
  implicit val mappingOfFavoriteId = MappedColumnType.base[Favorite.Id, Long](id => id, Favorite.Id(_))
  implicit val mappingOfTweetId = MappedColumnType.base[Tweet.Id, Long](id => id, Tweet.Id(_))
  implicit val mappingOfUserBlockId = MappedColumnType.base[UserBlock.Id, Long](id => id, UserBlock.Id(_))
}
