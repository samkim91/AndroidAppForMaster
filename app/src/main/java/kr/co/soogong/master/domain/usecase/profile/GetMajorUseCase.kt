package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Major
import javax.inject.Inject

@Reusable
class GetMajorUseCase @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<List<Major>> {
        return getRequiredInformationUseCase().map {
            it.majors
        }
    }
}