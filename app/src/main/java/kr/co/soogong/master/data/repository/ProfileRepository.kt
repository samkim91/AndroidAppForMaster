package kr.co.soogong.master.data.repository

import android.content.Context
import android.net.Uri
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterBasicDataInSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.utility.MultipartGenerator
import java.net.HttpURLConnection
import javax.inject.Inject

@Reusable
class ProfileRepository @Inject constructor(
    private val profileService: ProfileService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val saveMasterBasicDataInSharedUseCase: SaveMasterBasicDataInSharedUseCase,
    //    private val masterDao: MasterDao,
    @ApplicationContext private val context: Context,
) {

    fun getMaster(): Single<MasterDto> =
        profileService.getMasterByUid(getMasterUidFromSharedUseCase()!!)
            .doOnSuccess { it.data?.run { saveMasterBasicDataInSharedUseCase(this) } }
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }

    fun getMasterSimpleInfo(): Single<MasterDto> =
        profileService.getMasterSimpleInfo(getMasterUidFromSharedUseCase()!!)
            .doOnSuccess {
                it.data?.run { saveMasterBasicDataInSharedUseCase(this) }
            }
            .map { responseDto ->
                if (responseDto.code.toInt() == HttpURLConnection.HTTP_OK) {
                    responseDto.data
                } else {
                    throw Exception(responseDto.messageKo)
                }
            }

    // NOTE: 2022/01/27 차후 적용
    fun saveMaster(
        masterDto: MasterDto,
        profileImageUri: Uri? = null,
        businessRegistImageUri: Uri? = null,
        shopImagesUris: List<Uri>? = null,
    ): Single<MasterDto> =
        profileService.saveMaster(
            masterDto = MultipartGenerator.createJson(masterDto),
            profileImage = MultipartGenerator.createFile(context, "profileImage", profileImageUri),
            businessRegistImage = MultipartGenerator.createFile(context,
                "businessRegistImage",
                businessRegistImageUri),
            shopImages = MultipartGenerator.createFiles(context, "shopImages", shopImagesUris)
        )


    companion object {
        private const val TAG = "ProfileRepository"
    }
}