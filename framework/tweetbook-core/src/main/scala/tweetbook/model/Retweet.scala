package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * リツイート
 */
import Retweet._
case class Retweet(
  id:        Option[Id],          // リツートのID
  tweetId:   Tweet.Id,            // リツイートされたツイートID
  userId:    User.Id,             // リツイートしたユーザーID
  updatedAt: LocalDateTime = NOW, // 最終更新日時
  createdAt: LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object Retweet {
  // モデルのID型
  type Id = Long @@ Retweet
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, Retweet]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, Retweet]
}
