package kr.co.soogong.master.domain.usecase.common.major

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.co.soogong.master.data.source.network.common.major.MajorService
import kr.co.soogong.master.domain.entity.common.major.Project
import javax.inject.Inject

@Reusable
class GetProjectsUseCase @Inject constructor(
    private val majorService: MajorService,
) {
    suspend operator fun invoke(categoryId: Int): List<Project> =
        withContext(Dispatchers.IO) {
            Project.fromDtos(majorService.getProjects(categoryId))!!
        }
}