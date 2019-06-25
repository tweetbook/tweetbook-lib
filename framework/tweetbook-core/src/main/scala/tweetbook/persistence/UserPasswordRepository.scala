package tweetbook.persistence

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import tweetbook.model.{UserPassword, User}

case class UserPasswordRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[User.Id, UserPassword, P]
    with db.SlickResourceProvider[P] {
  import api._

  /*
   * ユーザーIDを元に該当するパスワード情報を取得する
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserPasswordTable, "slave") { query =>
      query
        .filter(_.id === id)
        .result
        .headOption
    }

  /*
   * パスワード情報を更新する
   */
  def update(password: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserPasswordTable) { query =>
      val row = query.filter(_.id === password.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => query += password.v
          case Some(_) => row.update(password.v)
        }
      } yield old
    }

  /*
   * ユーザーIDを元に該当するパスワード情報を削除する
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserPasswordTable) { query =>
      val row = query.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }

  @deprecated("use update method", "0.1.0") // 既存のユーザーIDを介して作成、更新するため
  def add(passwd: EntityWithNoId): Future[Id] =
    Future.failed(new UnsupportedOperationException)
}
