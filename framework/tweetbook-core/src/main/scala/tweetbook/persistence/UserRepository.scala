package tweetbook.persistence

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import tweetbook.model.User

case class UserRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[User.Id, User, P]
    with db.SlickResourceProvider[P] {
  import api._

  def get(id: Id): Future[Option[EntityEmbeddedId]] = RunDBAction(UserTable, "slave") { query =>
    query
      .filter(_.id === id)
      .result
      .headOption
  }

  def add(entity: EntityWithNoId): Future[Id] = RunDBAction(UserTable) { query =>
    query
      .returning(query.map(_.id))
      .+=(entity.v)
  }

  def update(entity: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] = RunDBAction(UserTable) { query =>
    val row = query.filter(_.id === entity.id)
    for {
      old <- row.result.headOption
      _   <- old match {
        case None    => DBIO.successful(0)
        case Some(_) => row.update(entity.v)
      }
    } yield old
  }

  def remove(id: Id): Future[Option[EntityEmbeddedId]] = RunDBAction(UserTable) { query =>
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
