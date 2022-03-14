package kr.co.soogong.master.domain.entity.common

sealed interface ICodeTable {
    val code: String
    val inKorean: String
}