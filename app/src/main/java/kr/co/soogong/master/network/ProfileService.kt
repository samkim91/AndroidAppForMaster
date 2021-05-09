package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.data.profile.PriceByProject
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

        return Portfolio.NULL_PORTFOLIO
        // Todo.. 이후 작업해야함
        // return Portfolio.fromJson(profileInterface.getPortfolio(query))
    }

    fun savePortfolio(portfolio: Portfolio): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = portfolio.masterId
        query["job_title"] = portfolio.title
        query["image_before_job"] = portfolio.imageBeforeJob
        query["image_after_job"] = portfolio.imageAfterJob
        query["job_description"] = portfolio.description

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePortfolio(query)
    }

    fun getPriceByProject(masterId: String, priceByProjectId: Int): PriceByProject {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
        query["price_by_project_id"] = priceByProjectId.toString()

        return PriceByProject.NULL_PRICE_BY_PROJECT
        // Todo.. server 이후로 작업해야함
        // return PriceByProject.fromJson(profileInterface.getPriceByProject(query))
    }

    fun savePriceByProject(priceByProject: PriceByProject): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = priceByProject.masterId
        query["job_title"] = priceByProject.title
        query["price"] = priceByProject.projectPrice
        query["job_description"] = priceByProject.description

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }


    fun deletePortfolio(masterId: String, itemId: Int): Single<Response> {
        val query = HashMap<String, Any>()
        query["master_id"] = masterId
        query["item_id"] = itemId

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.deletePortfolio(query)
    }

    fun deletePriceByProject(masterId: String, itemId: Int): Single<Response> {
        val query = HashMap<String, Any>()
        query["master_id"] = masterId
        query["item_id"] = itemId

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.deletePortfolio(query)
    }

    suspend fun getPortfolioList(masterId: String): List<Portfolio> {
        val query = HashMap<String, Any>()

        query["master_id"] = masterId

        return listOf(Portfolio.NULL_PORTFOLIO, Portfolio.NULL_PORTFOLIO, Portfolio.NULL_PORTFOLIO)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.getPortfolioList(query)
    }

    suspend fun getPriceByProjectList(masterId: String): List<PriceByProject> {
        val query = HashMap<String, Any>()

        query["master_id"] = masterId

        return listOf(PriceByProject.NULL_PRICE_BY_PROJECT, PriceByProject.NULL_PRICE_BY_PROJECT, PriceByProject.NULL_PRICE_BY_PROJECT)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.getPortfolioList(query)
    }

}