package kr.co.soogong.master.data.model.profile

interface IPortfolio {
    val id: Int
    val title: String
    val description: String?
    val project: String?
    val type: String
}