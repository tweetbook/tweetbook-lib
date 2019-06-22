package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

/*
 * ユーザー間のダイレクトメッセージ
 */
import DirectMessage._
case class DirectMessage(
  id:         Option[Id],          // ダイレクトメッセージのID
  senderId:   User.Id,             // 送信者のユーザーID
  receiverId: User.Id,             // 受信者のユーザーID
  body:       String,              // メッセージ本分
  updatedAt:  LocalDateTime = NOW, // 最終更新日時
  createdAt:  LocalDateTime = NOW  // 作成日時
) extends EntityModel[Id]

/*
 * コンパニオンオブジェクト
 */
object DirectMessage {
  // モデルのID型
  type Id = Long @@ Tweet
  val Id = the[Identity[Id]]

  // ID付与前のモデルの型
  type WithNoId = Entity.WithNoId[Id, DirectMessage]
  // ID付与後のモデルの型
  type EmbeddedId = Entity.EmbeddedId[Id, DirectMessage]
}
