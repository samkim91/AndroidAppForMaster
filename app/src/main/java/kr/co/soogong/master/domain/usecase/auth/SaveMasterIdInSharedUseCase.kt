package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveMasterIdInSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(masterId: Int) {
        sharedPreferences.edit()
            .putInt(AppSharedPreferenceContract.MASTER_ID, masterId)
            .apply()
    }
}