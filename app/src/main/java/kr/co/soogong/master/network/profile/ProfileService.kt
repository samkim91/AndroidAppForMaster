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

    fun getMaster(uid: String?): Single<MasterDto> {
        return profileInterface.getMasterByUid(uid)
    }



    fun savePortfolio(masterId: String, portfolio: Portfolio): Single<Response> {
        val query = HashMap<String, String>()


        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePortfolio(query)
    }

    fun savePriceByProject(masterId: String, priceByProject: PriceByProject): Single<Response> {
        val query = HashMap<String, String>()


        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }



}