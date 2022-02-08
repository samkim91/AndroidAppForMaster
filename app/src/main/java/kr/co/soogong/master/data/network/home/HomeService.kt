package kr.co.soogong.master.data.network.home

import retrofit2.Retrofit
import javax.inject.Inject

class HomeService @Inject constructor(
    retrofit: Retrofit,
) {
    private val homeInterface = retrofit.create(HomeInterface::class.java)

    fun getRequirementTotal(masterUid: String) = homeInterface.getRequirementTotal(masterUid)
}