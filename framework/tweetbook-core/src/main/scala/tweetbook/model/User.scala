package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * Tweetbookのユーザー
 */
import User._
case class User(
  id:          Option[Id],            // ユーザーID
  name:        String,                // ユーザー名
  email:       String,                // Emailアドレス
  description: Option[String] = None, // プロフィール文
  iconUrl:     Option[String] = None, // ユーザーのアイコンのURL
  updatedAt:   LocalDateTime = NOW,   // 最終更新日時
  createdAt:   LocalDateTime = NOW    // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object User {
  // モデルのID型
  type Id = Long @@ User
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, User]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, User]
}
