package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// リツイート
import Retweet._
case class Retweet(
  id:        Option[Id],
  tweetId:   Tweet.Id,            // リツイートされたツイートID
  userId:    User.Id,             // リツイートしたユーザーID
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Retweet {
  val Id = the[Identity[Id]]
  type Id = Long @@ Retweet
  type WithNoId = Entity.WithNoId[Id, Retweet]
  type EmbeddedId = Entity.EmbeddedId[Id, Retweet]
}
