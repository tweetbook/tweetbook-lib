package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// ユーザーのフォロー・フォロワー関係
import Follow._
case class Follow(
  id:        Option[Id],
  from:      User.Id,             // フォローしたユーザーID
  to:        User.Id,             // フォローされたユーザーID
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Follow {
  val Id = the[Identity[Id]]
  type Id = Long @@ Follow
  type WithNoId = Entity.WithNoId[Id, Follow]
  type EmbeddedId = Entity.EmbeddedId[Id, Follow]
}
