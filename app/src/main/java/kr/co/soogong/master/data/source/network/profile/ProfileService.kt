package kr.co.soogong.master.data.source.network.profile

import io.reactivex.Single
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileService @Inject constructor(
    retrofit: Retrofit,
) {
    private val profileInterface = retrofit.create(ProfileInterface::class.java)

    fun getMasterSettings(uid: String): Single<ResponseDto<MasterSettingsDto>> {
        return profileInterface.getMasterSettings(uid)
    }

    fun getMasterByUid(uid: String): Single<ResponseDto<MasterDto>> {
        return profileInterface.getMasterByUid(uid)
    }

    fun saveMaster(
        masterDto: RequestBody,
        profileImage: MultipartBody.Part?,
        businessRegistImage: MultipartBody.Part?,
        shopImages: List<MultipartBody.Part?>?,
    ): Single<MasterDto> {
        return profileInterface.saveMaster(masterDto, profileImage, businessRegistImage, shopImages)
    }


    suspend fun savePortfolio(
        savePortfolioJson: RequestBody,
        beforeImageFile: MultipartBody.Part?,
        afterImageFile: MultipartBody.Part?,
    ) {
        profileInterface.savePortfolio(savePortfolioJson, beforeImageFile, afterImageFile)
    }

    suspend fun saveRepairPhoto(
        saveRepairPhotoJson: RequestBody,
        imageFiles: List<MultipartBody.Part?>?,
    ) {
        profileInterface.saveRepairPhoto(saveRepairPhotoJson, imageFiles)
    }

    suspend fun savePriceByProject(
        savePriceByProjectJson: RequestBody,
    ) {
        profileInterface.savePriceByProject(savePriceByProjectJson)
    }

    fun getPortfolios(
        type: String,
        uid: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<ResponseDto<PageableContentDto<PortfolioDto>>> {
        return profileInterface.getPortfolios(type, uid, offset, pageSize, order, orderBy)
    }

    fun deletePortfolio(
        portfolioId: Int,
    ): Single<ResponseBody> {
        return profileInterface.deletePortfolio(portfolioId)
    }

    suspend fun updateFreeMeasureYn(
        masterDto: MasterDto,
    ) {
        return profileInterface.updateFreeMeasureYn(masterDto)
    }

    fun updateDirectRepairYn(
        masterDto: MasterDto,
    ): Single<MasterDto> {
        return profileInterface.updateDirectRepairYn(masterDto)
    }

    fun updateRequestMeasureYn(
        uid: String,
    ): Single<MasterDto> {
        return profileInterface.updateRequestMeasureYn(uid)
    }

}