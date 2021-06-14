package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveMasterIdInSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(masterUid: String) {
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.MASTER_UID, masterUid)
            .apply()
    }
}