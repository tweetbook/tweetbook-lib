package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import Follow._
case class Follow(
  id: Option[Id],
  followerId: User.Id
  followeeId: User.Id
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Follow {
  val  Id         = the[Identity[Id]]
  type Id         = Long @@ Follow
  type WithNoId   = Entity.WithNoId   [Id, Follow]
  type EmbeddedId = Entity.EmbeddedId [Id, Follow]
}

