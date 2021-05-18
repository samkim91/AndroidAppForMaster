package kr.co.soogong.master.domain.usecase.auth

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.domain.usecase.profile.GetProfileFromLocalUseCase
import kr.co.soogong.master.network.Response
import javax.inject.Inject

@Reusable
class RequestCertificationCodeUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(phoneNumber: String): Single<Response> {
        if (BuildConfig.DEBUG) {

        }

        return Single.just(Response.TEST_RESPONSE)
    }
}