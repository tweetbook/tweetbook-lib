package tweetbook.model

import java.time.LocalDateTime
import ixias.model._

// ツイート
import Tweet._
case class Tweet(
    id:        Option[Id],
    authorId:  User.Id,             // ツイートしたユーザーID
    replyTo:   Option[Id] = None,   // リプライ先のツイートID (リプライの場合のみ)
    content:   String,              // ツイートの内容
    updatedAt: LocalDateTime = NOW,
    createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

object Tweet {
  val Id = the[Identity[Id]]
  type Id = Long @@ Tweet
  type WithNoId = Entity.WithNoId[Id, Tweet]
  type EmbeddedId = Entity.EmbeddedId[Id, Tweet]
}
