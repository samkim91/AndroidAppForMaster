package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveAccessTokenUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(accessToken: String?) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.ACCESS_TOKEN, accessToken)
            .apply()
    }
}