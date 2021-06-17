package kr.co.soogong.master.data.model.profile

import java.util.*

interface IPortfolio {
    val id: Int
    val title: String
    val description: String
    val project: String?
    val type: String
    val createdAt: Date?
    val updatedAt: Date?
}