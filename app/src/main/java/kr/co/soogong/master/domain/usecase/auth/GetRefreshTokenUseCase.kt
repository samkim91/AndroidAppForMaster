package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class GetRefreshTokenUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String? {
        return sharedPreferences.getString(AppSharedPreferenceContract.REFRESH_TOKEN, "")
    }
}