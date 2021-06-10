package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPhoneAuthCredentialUseCase @Inject constructor() {
    operator fun invoke(verificationId: String, code: String): PhoneAuthCredential {
        return PhoneAuthProvider.getCredential(verificationId, code)
    }
}