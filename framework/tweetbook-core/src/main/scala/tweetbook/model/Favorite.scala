package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ツイートへのいいね
 */
import Favorite._
case class Favorite(
  id:        Option[Id],          // いいねのID
  tweetId:   Tweet.Id,            // いいねされたツイートID
  userId:    User.Id,             // いいねしたユーザーID
  updatedAt: LocalDateTime = NOW, // 最終更新日時
  createdAt: LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object Favorite {
  // モデルのID型
  type Id = Long @@ Favorite
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, Favorite]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, Favorite]
}
