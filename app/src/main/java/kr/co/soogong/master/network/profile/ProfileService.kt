package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.*
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    fun getMaster(masterUid: String?): Single<MasterDto> {
        return profileInterface.getMasterByUid(masterUid)
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

    fun savePriceByProject(masterId: String, priceByProject: PriceByProject): Single<Response> {
        val query = HashMap<String, String>()
        query["master_id"] = masterId
        query["job_title"] = priceByProject.title
        query["price"] = priceByProject.price
        query["job_description"] = priceByProject.description

        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }



}