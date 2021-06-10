package kr.co.soogong.master.domain.usecase.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Reusable
import kr.co.soogong.master.ui.auth.signup.LimitTime
import kr.co.soogong.master.utility.PhoneNumberHelper
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class ResendVerificationCodeUseCase @Inject constructor() {
    operator fun invoke(
        callbackActivity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        firebaseAuth: FirebaseAuth,
        phoneNumber: String,
        resendToken: PhoneAuthProvider.ForceResendingToken?,
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(PhoneNumberHelper.toGlobalNumber(phoneNumber))
            .setTimeout(LimitTime, TimeUnit.SECONDS)
            .setActivity(callbackActivity)
            .setCallbacks(callbacks)
        if (resendToken != null) {
            optionsBuilder.setForceResendingToken(resendToken)          // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
}