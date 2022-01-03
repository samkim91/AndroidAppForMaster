package kr.co.soogong.master.domain.usecase.major

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Project
import kr.co.soogong.master.network.major.MajorService
import javax.inject.Inject

@Reusable
class GetProjectsUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    operator fun invoke(categoryId: Int): Single<List<Project>> =
        majorService.getProjects(categoryId).map { projectDtos ->
            Project.fromProjectDtos(projectDtos)
        }
}