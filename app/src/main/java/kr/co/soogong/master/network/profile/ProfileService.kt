package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    fun getMasterSimpleInfo(uid: String?): Single<MasterDto> {
        return profileInterface.getMasterSimpleInfo(uid)
    }

    fun getMasterByUid(uid: String?): Single<MasterDto> {
        return profileInterface.getMasterByUid(uid)
    }

    fun saveMaster(
        masterDto: RequestBody,
        profileImage: MultipartBody.Part? = null,
        businessRegistImage: MultipartBody.Part? = null,
        shopImages: List<MultipartBody.Part?>? = null,
    ): Single<MasterDto> {
        return profileInterface.saveMaster(masterDto, profileImage, businessRegistImage, shopImages)
    }

    fun savePortfolio(
        portfolioDto: RequestBody,
        beforeImageFile: MultipartBody.Part?,
        afterImageFile: MultipartBody.Part?
    ): Single<PortfolioDto> {
        return profileInterface.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }

    fun updateRequestMeasureYn(
        uid: String,
    ): Single<MasterDto> {
        return profileInterface.updateRequestMeasureYn(uid)
    }

    fun updateFreeMeasureYn(
        masterDto: MasterDto,
    ): Single<MasterDto> {
        return profileInterface.updateFreeMeasureYn(masterDto)
    }

    fun getPortfoliosByUid(uid: String?): Single<List<PortfolioDto>> {
        return profileInterface.getPortfoliosByUid(uid)
    }

    fun updateUidByTel(tel: String, uid: String): Single<MasterDto> {
        return profileInterface.updateUidByTel(tel, uid)
    }

    fun deletePortfolio(
        portfolioId: Int
    ): Single<ResponseBody> {
        return profileInterface.deletePortfolio(portfolioId)
    }

}