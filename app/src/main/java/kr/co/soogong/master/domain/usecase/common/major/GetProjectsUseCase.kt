package kr.co.soogong.master.domain.usecase.common.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.data.source.network.common.major.MajorService
import javax.inject.Inject

@Reusable
class GetProjectsUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    operator fun invoke(categoryId: Int): Single<List<Project>> =
        majorService.getProjects(categoryId).map { projectDtos ->
            Project.fromDtos(projectDtos)
        }
}