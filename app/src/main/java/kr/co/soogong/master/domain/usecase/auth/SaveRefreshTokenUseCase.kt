package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveRefreshTokenUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(refreshToken: String?) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.REFRESH_TOKEN, refreshToken)
            .apply()
    }
}