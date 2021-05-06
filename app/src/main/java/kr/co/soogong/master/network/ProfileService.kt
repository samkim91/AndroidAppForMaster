package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.user.User
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    suspend fun getUserProfile(keycode: String?): User {
        return User.fromJson(profileInterface.getUserProfile(keycode))
    }

    fun getPortfolio(masterId: String, portfolioId: Int): Portfolio {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
        query["portfolio_id"] = portfolioId.toString()

        return Portfolio.fromJson(profileInterface.getPortfolio(query))
    }

    fun setPortfolio(portfolio: Portfolio): Single<Response> {
        val data = HashMap<String, String>()
        data["master_id"] = portfolio.masterId
        data["job_title"] = portfolio.jobTitle
        data["image_before_job"] = portfolio.imageBeforeJob
        data["image_after_job"] = portfolio.imageAfterJob
        data["job_description"] = portfolio.jobDescription

        return profileInterface.setPortfolio(data)
    }
}