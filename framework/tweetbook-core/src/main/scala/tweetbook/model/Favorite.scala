package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import Favorite._
case class Favorite(
  id: Option[Id],
  tweetId: Tweet.Id,
  userId: User.Id,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Favorite {
  val  Id = the[Identity[Id]]
  type Id = Long @@ Favorite
  type WithNoId   = Entity.WithNoId   [Id, Favorite]
  type EmbeddedId = Entity.EmbeddedId [Id, Favorite]
}

