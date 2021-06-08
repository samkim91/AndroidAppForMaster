package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetMasterIdFromFirebaseUseCase @Inject constructor() {
    operator fun invoke(): String? {
        return Firebase.auth.currentUser?.uid;
    }
}