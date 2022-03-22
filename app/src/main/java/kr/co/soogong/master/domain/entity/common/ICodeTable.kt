package kr.co.soogong.master.domain.entity.common

interface ICodeTable {
    val code: String
    val inKorean: String
}

interface ICodeTableFun {
    fun getCodeTableByCode(keyword: String): ICodeTable?
    fun getCodeTableByKorean(keyword: String): ICodeTable?
}