package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.domain.usecase.auth.SaveMasterBasicDataInSharedUseCase
import kr.co.soogong.master.network.profile.ProfileService
import javax.inject.Inject

@Reusable
class GetMasterSimpleInfoUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
    private val saveMasterBasicDataInSharedUseCase: SaveMasterBasicDataInSharedUseCase,
) {
    operator fun invoke(): Single<MasterDto> {
        return profileService.getMasterSimpleInfo(getMasterUidFromSharedUseCase())
            .doOnSuccess {
                saveMasterBasicDataInSharedUseCase(it)
            }
    }
}