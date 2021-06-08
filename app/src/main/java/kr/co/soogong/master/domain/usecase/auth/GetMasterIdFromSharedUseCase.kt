package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class GetMasterIdFromSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String? {
        return sharedPreferences.getString(AppSharedPreferenceContract.MASTER_ID, "")
    }
}