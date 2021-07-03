package kr.co.soogong.master.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    fun getMasterByUid(uid: String?): Single<MasterDto> {
        return profileInterface.getMasterByUid(uid)
    }

    fun saveMaster(
        masterDto: MasterDto,
        profileImage: MultipartBody.Part?,
        businessRegistImage: MultipartBody.Part?,
        thumbnails: List<MultipartBody.Part>?
    ): Single<MasterDto> {
        return profileInterface.saveMaster(masterDto)
    }

    fun savePortfolio(
        portfolioDto: RequestBody,
        beforeImageFile: MultipartBody.Part?,
        afterImageFile: MultipartBody.Part?
    ): Single<PortfolioDto> {
        return profileInterface.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }

    fun getPortfoliosByUid(uid: String?): Single<List<PortfolioDto>> {
        return profileInterface.getPortfoliosByUid(uid)
    }

}