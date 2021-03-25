package kr.co.soogong.master.domain.usecase

import android.content.SharedPreferences
import kr.co.soogong.master.domain.AppSharedPreferenceContract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMasterKeyCodeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String? {
        return sharedPreferences.getString(AppSharedPreferenceContract.BRANCH_KEYCODE, null)
    }
}