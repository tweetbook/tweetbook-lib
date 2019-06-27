package tweetbook.persistence

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import tweetbook.model.UserBlock

case class UserBlockRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[UserBlock.Id, UserBlock, P]
    with db.SlickResourceProvider[P] {
  import api._

  /*
   * ユーザーブロックIDから該当するユーザーブロック関係を取得する
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] = RunDBAction(UserBlockTable, "slave") { query =>
    query
      .filter(_.id === id)
      .result
      .headOption
  }


  /*
   * ユーザーブロック関係を追加する
   */
  def add(entity: EntityWithNoId): Future[Id] =
    RunDBAction(UserBlockTable) { query =>
      query
        .returning(query.map(_.id))
        .+=(entity.v)
    }

  /*
   * ユーザーブロック関係を更新する
   */
  def update(entity: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserBlockTable) { query =>
      val row = query.filter(_.id === entity.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.update(entity.v)
        }
      } yield old
    }

  /*
   * ユーザーブロックIDから該当するユーザーブロック関係を削除する
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserBlockTable) { query =>
      val row = query.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }
}
