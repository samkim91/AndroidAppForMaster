package kr.co.soogong.master.domain.usecase.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Reusable
import kr.co.soogong.master.ui.auth.signup.LimitTime
import kr.co.soogong.master.ui.utils.PhoneNumberHelper
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class RequestVerificationCodeUseCase @Inject constructor() {
    operator fun invoke(
        callbackActivity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        firebaseAuth: FirebaseAuth,
        phoneNumber: String,
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))      // Phone number to verify
            .setTimeout(LimitTime, TimeUnit.SECONDS)                            // Timeout and unit
            .setActivity(callbackActivity)                                      // Activity (for callback binding)
            .setCallbacks(callbacks)                                            // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}