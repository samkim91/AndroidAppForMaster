package kr.co.soogong.master.domain.usecase

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SetRefreshTokenUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(token: String?) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.REFRESH_TOKEN, token)
            .apply()
    }
}