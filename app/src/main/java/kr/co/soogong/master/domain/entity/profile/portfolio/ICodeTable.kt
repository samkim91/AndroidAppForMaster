package kr.co.soogong.master.domain.entity.profile.portfolio

sealed interface ICodeTable {
    val code: String
    val inKorean: String
}