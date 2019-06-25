package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ユーザー認証時のパスワード
 */
case class UserPassword(
  id:        Option[User.Id],     // ユーザーID
  hashed:    String,              // ハッシュ化後のパスワード
  salt:      String,              // ハッシュ化時のソルト
  updatedAt: LocalDateTime = NOW, // 最終更新日時
  createdAt: LocalDateTime = NOW  // 作成日時
) extends EntityModel[User.Id]

/*
 * コンパニオンオブジェクト
 */
object UserPassword {
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[User.Id, UserPassword]
}
