package kr.co.soogong.master.domain.usecase.preferences

import dagger.Reusable
import kr.co.soogong.master.domain.repository.PreferencesRepository
import javax.inject.Inject

@Reusable
class UpdateNoticeIsNewUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    operator fun invoke(id: Int) =
        preferencesRepository.updateNoticeIsNew(id)
}