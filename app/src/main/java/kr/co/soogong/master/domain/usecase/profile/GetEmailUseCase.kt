package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetEmailUseCase @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) {
    operator fun invoke(): Single<String> {
        return getProfileUseCase().map {
            it.basicInformation?.email
        }
    }
}