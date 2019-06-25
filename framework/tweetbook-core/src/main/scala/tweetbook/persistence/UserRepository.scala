package tweetbook.persistence

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import tweetbook.model.User

case class UserRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[User.Id, User, P]
    with db.SlickResourceProvider[P] {
  import api._

  /*
   * ユーザーIDから該当するユーザー情報を取得する
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable, "slave") { query =>
      query
        .filter(_.id === id)
        .result
        .headOption
    }

  /*
   * メールアドレスから該当するユーザ情報を取得する
   */
  def findByEmail(email: String): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable, "slave") { query =>
      query
        .filter(_.email === email)
        .result
        .headOption
    }

  /*
   * 複数のユーザーIDから該当するユーザー情報を取得する
   */
  def filterByIds(ids: Traversable[User.Id]): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(UserTable, "slave") { query =>
      query
        .filter(_.id inSet ids)
        .result
    }


  /*
   * ユーザー情報を追加する
   */
  def add(entity: EntityWithNoId): Future[Id] =
    RunDBAction(UserTable) { query =>
      query
        .returning(query.map(_.id))
        .+=(entity.v)
    }

  /*
   * ユーザー情報を更新する
   */
  def update(entity: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable) { query =>
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
   * ユーザーIDから該当するユーザー情報を削除する
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable) { query =>
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
