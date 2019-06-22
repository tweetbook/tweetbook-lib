package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ユーザー間のブロック関係
 */
import UserBlock._
case class UserBlock(
  id:        Option[Id],          // ID
  from:      User.Id,             // ブロックしたユーザーID
  to:        User.Id,             // ブロックされたユーザーID
  updatedAt: LocalDateTime = NOW, // 最終更新日時
  createdAt: LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object UserBlock {
  // モデルのID型
  type Id = Long @@ UserBlock
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, UserBlock]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, UserBlock]
}
