package kr.co.soogong.master.domain.usecase.preferences

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.data.repository.PreferencesRepository
import javax.inject.Inject

@Reusable
class GetVersionUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Single<VersionDto> {
        return preferencesRepository.getVersion()
    }
}