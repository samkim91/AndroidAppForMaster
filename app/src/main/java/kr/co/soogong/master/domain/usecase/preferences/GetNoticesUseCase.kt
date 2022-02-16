package kr.co.soogong.master.domain.usecase.preferences

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.entity.preferences.Notice
import kr.co.soogong.master.data.repository.PreferencesRepository
import javax.inject.Inject

@Reusable
class GetNoticesUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    operator fun invoke(): Single<List<Notice>> =
        preferencesRepository.getNotices(sections = listOf("Master", "Common"))
            .map { list ->
                list.map {
                    Notice.fromNoticeDto(it)
                }
            }
}