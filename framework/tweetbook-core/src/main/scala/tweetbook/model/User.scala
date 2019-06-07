package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// ユーザー
import User._
case class User(
  id:          Option[Id],
  name:        String,                // ユーザー名
  email:       String,                // Emailアドレス
  description: Option[String] = None, // プロフィール文
  iconUrl:     Option[String] = None, // アイコンのURL
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object User {
  val Id = the[Identity[Id]]
  type Id = Long @@ User
  type WithNoId = Entity.WithNoId[Id, User]
  type EmbeddedId = Entity.EmbeddedId[Id, User]
}
