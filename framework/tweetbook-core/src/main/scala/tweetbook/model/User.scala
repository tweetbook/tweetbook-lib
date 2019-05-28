package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import User._
case class User(
  id: Option[Id],
  name: String,
  email: String,
  description: Option[String] = None,
  iconUrl: Option[String] = None,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object User {
  val  Id = the[Identity[Id]]
  type Id = Long @@ User
  type WithNoId   = Entity.WithNoId   [Id, User]
  type EmbeddedId = Entity.EmbeddedId [Id, User]
}
