package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import DirectMessage._
case class DirectMessage(
  id: Option[Id],
  body: String,
  senderId: User.Id,
  receiverId: User.Id,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object DirectMessage {
  val Id = the[Identity[Id]]
  type Id = Long @@ Tweet
  type WithNoId = Entity.WithNoId[Id, DirectMessage]
  type EmbeddedId = Entity.EmbeddedId[Id, DirectMessage]
}
