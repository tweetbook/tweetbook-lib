package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// ユーザー間のブロック関係
import UserBlock._
case class UserBlock(
  id:        Option[Id],
  from:      User.Id,             // ブロックしたユーザーID
  to:        User.Id,             // ブロックされたユーザーID
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object UserBlock {
  val Id = the[Identity[Id]]
  type Id = Long @@ UserBlock
  type WithNoId = Entity.WithNoId[Id, UserBlock]
  type EmbeddedId = Entity.EmbeddedId[Id, UserBlock]
}
