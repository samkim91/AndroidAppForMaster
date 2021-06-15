package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetIntroductionUseCase @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<String> {
        return getRequiredInformationUseCase().map {
            it.introduction
        }
    }
}