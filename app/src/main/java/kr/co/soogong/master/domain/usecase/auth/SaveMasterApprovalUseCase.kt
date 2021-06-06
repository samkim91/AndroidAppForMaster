package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveMasterApprovalUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(approval: Boolean) {
        sharedPreferences.edit()
            .putBoolean(AppSharedPreferenceContract.IS_APPROVED, approval)
            .apply()
    }
}