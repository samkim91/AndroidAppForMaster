package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    fun getMasterByUid(uid: String?): Single<MasterDto> {
        return profileInterface.getMasterByUid(uid)
    }

    fun saveMaster(masterDto: MasterDto): Single<MasterDto> {
        return profileInterface.saveMaster(masterDto)
    }





    fun savePortfolio(masterId: String, portfolio: PortfolioDto): Single<Response> {
        val query = HashMap<String, String>()


        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePortfolio(query)
    }

    fun savePriceByProject(masterId: String, priceByProject: PortfolioDto): Single<Response> {
        val query = HashMap<String, String>()


        return Single.just(Response.NULL_RESPONSE)
        // Todo.. server 이후로 작업해야함
        // return profileInterface.savePriceByProject(query)
    }


}