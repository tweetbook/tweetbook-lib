package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ツイート
 */
import Tweet._
case class Tweet(
    id:        Option[Id],          // ツイートのID
    userId:    User.Id,             // ツイートしたユーザーID
    replyTo:   Option[Id] = None,   // リプライ先のツイートID (リプライの場合のみ)
    content:   String,              // ツイートの内容
    updatedAt: LocalDateTime = NOW, // 最終更新日時
    createdAt: LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object Tweet {
  // モデルのID型
  type Id = Long @@ Tweet
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, Tweet]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, Tweet]
}
