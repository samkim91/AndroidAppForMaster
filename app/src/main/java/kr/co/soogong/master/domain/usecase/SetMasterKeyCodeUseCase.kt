package kr.co.soogong.master.domain.usecase

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SetMasterKeyCodeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(keyCode: String, isApproved: Boolean) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.BRANCH_KEYCODE, keyCode)
            .putBoolean(AppSharedPreferenceContract.IS_APPROVED, isApproved)
            .apply()
    }
}