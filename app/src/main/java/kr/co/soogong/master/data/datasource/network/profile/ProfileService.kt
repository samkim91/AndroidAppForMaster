package kr.co.soogong.master.data.datasource.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import kr.co.soogong.master.data.entity.requirement.review.ReviewDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit,
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    // profile repo 로 이동
    fun updateUidByTel(tel: String, uid: String): Single<MasterDto> {
        return profileInterface.updateUidByTel(tel, uid)
    }

    fun getMasterSettings(uid: String): Single<ResponseDto<MasterSettingsDto>> {
        return profileInterface.getMasterSettings(uid)
    }

    fun getMasterByUid(uid: String): Single<ResponseDto<MasterDto>> {
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
        afterImageFile: MultipartBody.Part?,
    ): Single<PortfolioDto> {
        return profileInterface.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }

    fun getPortfolios(
        uid: String,
        type: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<ResponseDto<PageableContentDto<PortfolioDto>>> {
        return profileInterface.getPortfolios(uid, type, offset, pageSize, order, orderBy)
    }

    fun deletePortfolio(
        portfolioId: Int,
    ): Single<ResponseBody> {
        return profileInterface.deletePortfolio(portfolioId)
    }

    fun getReviews(
        uid: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<ResponseDto<PageableContentDto<ReviewDto>>> =
        profileInterface.getReviews(uid, offset, pageSize, order, orderBy)

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

    fun updateDirectRepairYn(
        masterDto: MasterDto,
    ): Single<MasterDto> {
        return profileInterface.updateDirectRepairYn(masterDto)
    }

}