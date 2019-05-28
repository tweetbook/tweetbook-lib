package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import UserPassword._
case class UserPassword(
  id: Option[Id],
  hashedPassword: String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object UserPassword {
  type Id = User.Id
  type WithNoId = Entity.WithNoId[Id, UserPassword]
  type EmbeddedId = Entity.EmbeddedId[Id, UserPassword]
}
