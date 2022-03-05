package kr.co.soogong.master.data.repository

import android.content.SharedPreferences
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import kr.co.soogong.master.data.datasource.network.profile.ProfileService
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto
import kr.co.soogong.master.data.entity.profile.PortfolioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class ProfileRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val profileService: ProfileService,
    //    private val masterDao: MasterDao,
) {
    fun getMasterIdFromShared(): Int =
        sharedPreferences.getInt(AppSharedPreferenceContract.MASTER_ID, -1)

    fun getMasterUidFromShared(): String =
        sharedPreferences.getString(AppSharedPreferenceContract.MASTER_UID, "")!!

    fun saveMasterKeysInShared(id: Int, uid: String) {
        sharedPreferences.edit()
            .putInt(AppSharedPreferenceContract.MASTER_ID, id)
            .apply()

        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.MASTER_UID, uid)
            .apply()
    }

    fun updateUidByTel(tel: String, uid: String): Single<MasterDto> =
        profileService.updateUidByTel(tel, uid)

    fun getMaster(): Single<MasterDto> =
        profileService.getMasterByUid(getMasterUidFromShared())
            .doOnSuccess { it.data?.run { saveMasterKeysInShared(this.id!!, this.uid!!) } }
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }

    fun getMasterSettings(): Single<MasterSettingsDto> =
        profileService.getMasterSettings(getMasterUidFromShared())
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }

    suspend fun updateFreeMeasureYn(masterDto: MasterDto) =
        profileService.updateFreeMeasureYn(masterDto)

    // NOTE: 2022/01/27 차후 적용
//    fun saveMaster(
//        masterDto: MasterDto,
//        profileImageUri: Uri? = null,
//        businessRegistImageUri: Uri? = null,
//        shopImagesUris: List<Uri>? = null,
//    ): Single<MasterDto> =
//        profileService.saveMaster(
//            masterDto = MultipartGenerator.createJson(masterDto),
//            profileImage = MultipartGenerator.createFile(context, "profileImage", profileImageUri),
//            businessRegistImage = MultipartGenerator.createFile(context,
//                "businessRegistImage",
//                businessRegistImageUri),
//            shopImages = MultipartGenerator.createFiles(context, "shopImages", shopImagesUris)
//        )

    fun getPortfolios(
        type: String,
        offset: Int,
        pageSize: Int,
        order: Int,
        orderBy: String,
    ): Single<PageableContentDto<PortfolioDto>> =
        profileService.getPortfolios(getMasterUidFromShared(),
            type,
            offset,
            pageSize,
            order,
            orderBy)
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data?.let { pageableContentDto ->
                        PageableContentDto(
                            content = pageableContentDto.content,
                            pageable = pageableContentDto.pageable,
                            last = pageableContentDto.last,
                            numberOfElements = pageableContentDto.numberOfElements
                        )
                    }
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }

    fun savePortfolio(
        portfolioDto: RequestBody,
        beforeImageFile: MultipartBody.Part?,
        afterImageFile: MultipartBody.Part?,
    ): Single<PortfolioDto> {
        return profileService.savePortfolio(portfolioDto, beforeImageFile, afterImageFile)
    }

    fun deletePortfolio(id: Int): Single<ResponseBody> = profileService.deletePortfolio(id)


    companion object {
        private const val TAG = "ProfileRepository"
    }
}