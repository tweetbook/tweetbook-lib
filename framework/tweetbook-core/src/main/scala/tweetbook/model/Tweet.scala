package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import Tweet._
case class Tweet(
  id: Option[Id],
  body: String,
  authorId: User.Id,
  replyTo: Option[Id] = None,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Tweet {
  val  Id         = the[Identity[Id]]
  type Id         = Long @@ Tweet
  type WithNoId   = Entity.WithNoId   [Id, Tweet]
  type EmbeddedId = Entity.EmbeddedId [Id, Tweet]
}

