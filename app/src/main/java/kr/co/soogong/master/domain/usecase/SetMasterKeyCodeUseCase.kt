package kr.co.soogong.master.domain.usecase

import android.content.SharedPreferences
import kr.co.soogong.master.domain.AppSharedPreferenceContract
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SetMasterKeyCodeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(value: String) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.BRANCH_KEYCODE, value)
            .apply()
    }
}