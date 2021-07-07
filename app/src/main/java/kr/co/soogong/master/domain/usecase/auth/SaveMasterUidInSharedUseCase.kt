package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import kr.co.soogong.master.data.dto.profile.MasterDto
import javax.inject.Inject

@Reusable
class SaveMasterUidInSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(uid: String) {
        // save master uid
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.MASTER_UID, uid)
            .apply()
    }
}