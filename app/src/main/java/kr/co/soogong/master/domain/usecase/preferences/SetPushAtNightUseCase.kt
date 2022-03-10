package kr.co.soogong.master.domain.usecase.preferences

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.repository.PreferencesRepository
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import javax.inject.Inject

@Reusable
class SetPushAtNightUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase
) {
    suspend operator fun invoke() {
        return withContext(Dispatchers.IO) {
            preferencesRepository.setPushAtNight(getMasterUidFromSharedUseCase())
        }
    }
}