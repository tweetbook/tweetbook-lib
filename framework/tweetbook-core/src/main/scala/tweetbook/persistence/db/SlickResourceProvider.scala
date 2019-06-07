package tweetbook.persistence.db

import slick.jdbc.JdbcProfile

trait SlickResourceProvider[P <: JdbcProfile] extends SlickColumnTypes[P] {
  implicit val driver: P

  // --[ テーブル定義 ] --------------------------------------------------------
  object UserTable                 extends UserTable

  // --[ 全てのテーブル定義 ] --------------------------------------------------
  lazy val AllTables = Seq(
    UserTable
  )
}

