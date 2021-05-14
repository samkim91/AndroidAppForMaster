package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.profile.*
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

    suspend fun getProfile(masterId: String?): Profile {
        return Profile.fromJson(profileInterface.getProfile(masterId))
    }

    fun getPortfolio(masterId: String, portfolioId: Int): Portfolio {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
        query["portfolio_id"] = portfolioId.toString()

        return Portfolio.TEST_PORTFOLIO
        // Todo.. 이후 작업해야함
        // return Portfolio.fromJson(profileInterface.getPortfolio(query))
    }

    fun savePortfolio(masterId: String, portfolio: Portfolio): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
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

        return PriceByProject.TEST_PRICE_BY_PROJECT
        // Todo.. server 이후로 작업해야함
        // return PriceByProject.fromJson(profileInterface.getPriceByProject(query))
    }

    fun savePriceByProject(masterId: String, priceByProject: PriceByProject): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
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

        return listOf(Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO, Portfolio.TEST_PORTFOLIO)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.getPortfolioList(query)
    }

    suspend fun getPriceByProjectList(masterId: String): List<PriceByProject> {
        val query = HashMap<String, Any>()

        query["master_id"] = masterId

        return listOf(PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT, PriceByProject.TEST_PRICE_BY_PROJECT)
        // Todo.. server 이후로 작업해야함
//        return profileInterface.getPortfolioList(query)
    }

    fun saveFlexibleCost(masterId: String, flexibleCost: FlexibleCost): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
        query["travel_cost"] = flexibleCost.travelCost
        query["crane_usage"] = flexibleCost.craneUsage
        query["package_cost"] = flexibleCost.packageCost
        query["other_cost_information"] = flexibleCost.otherCostInformation

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }

    suspend fun getOtherFlexibleOptions(masterId: String): OtherFlexibleOptions {
        val query = HashMap<String, String>()
        query["master_id"] = masterId

        return OtherFlexibleOptions.TEST_OTHER_FLEXIBLE_OPTIONS
        // Todo.. server 이후로 작업해야함
//         return profileInterface.getOtherFlexibleOptions(query)
    }

    fun saveOtherFlexibleOptions(masterId: String, otherFlexibleOptions: OtherFlexibleOptions): Single<Response> {
        val query = HashMap<String, Any>()
        query["master_id"] = masterId
        query["other_flexible_options"] = otherFlexibleOptions.options

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }

    suspend fun getMyReviews(masterId: String): MyReview {
        val query = HashMap<String, String>()
        query["master_id"] = masterId

        return MyReview.TEST_MY_REVIEW
        // Todo.. server 이후로 작업해야함
//         return profileInterface.getOtherFlexibleOptions(query)
    }

    suspend fun getRequiredInformation(masterId: String): RequiredInformation {
        val query = HashMap<String, String>()
        query["master_id"] = masterId

        return RequiredInformation.TEST_REQUIRED_INFORMATION
        // Todo.. server 이후로 작업해야함
//         return profileInterface.getOtherFlexibleOptions(query)
    }

}