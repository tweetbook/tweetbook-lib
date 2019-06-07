package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// ダイレクトメッセージ
import DirectMessage._
case class DirectMessage(
  id:         Option[Id],
  senderId:   User.Id,             // 送信者のユーザーID
  receiverId: User.Id,             // 受信者のユーザーID
  body:       String,              // メッセージ本分
  updatedAt:  LocalDateTime = NOW,
  createdAt:  LocalDateTime = NOW
) extends EntityModel[Id]

object DirectMessage {
  val Id = the[Identity[Id]]
  type Id = Long @@ Tweet
  type WithNoId = Entity.WithNoId[Id, DirectMessage]
  type EmbeddedId = Entity.EmbeddedId[Id, DirectMessage]
}
