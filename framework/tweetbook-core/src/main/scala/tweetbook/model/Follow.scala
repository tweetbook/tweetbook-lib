package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ユーザーのフォロー・フォロワー関係
 */
import Follow._
case class Follow(
  id:         Option[Id],          // ID
  followerId: User.Id,             // フォローしたユーザーID
  followeeId: User.Id,             // フォローされたユーザーID
  updatedAt:  LocalDateTime = NOW, // 最終更新日時
  createdAt:  LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object Follow {
  // モデルのID型
  type Id = Long @@ Follow
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, Follow]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, Follow]
}
