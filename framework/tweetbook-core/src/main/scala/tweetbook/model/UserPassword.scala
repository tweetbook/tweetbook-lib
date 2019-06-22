package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ユーザー認証時のパスワード
 */
import UserPassword._
case class UserPassword(
  id:             Option[Id],          // ユーザーID
  hashedPassword: String,              // ハッシュ化後のパスワード
  updatedAt:      LocalDateTime = NOW, // 最終更新日時
  createdAt:      LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object UserPassword {
  // モデルのID型
  type Id = User.Id

  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, UserPassword]
}
