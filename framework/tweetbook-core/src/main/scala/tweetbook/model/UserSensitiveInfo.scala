package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

import UserSensitiveInfo._
case class UserSensitiveInfo(
  id: Option[Id],
  hashedPassword: String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object UserSensitiveInfo {
  type Id = User.Id
  type WithNoId   = Entity.WithNoId   [Id, UserSensitiveInfo]
  type EmbeddedId = Entity.EmbeddedId [Id, UserSensitiveInfo]
}

