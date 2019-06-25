package tweetbook

package object persistence {

  // デフォルトのリポジトリのインスタンスたち
  val default = onMySQL

  // MySQLProfileベースのリポジトリのインスタンスたち
  object onMySQL {
    implicit lazy val driver = slick.jdbc.MySQLProfile
    object UserRepository              extends UserRepository
    object UserPasswordRepository      extends UserPasswordRepository
    object TweetRepository             extends TweetRepository
    object FollowRepository            extends FollowRepository
  }
}
