package kr.co.soogong.master.domain.usecase.common

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class SaveNoticeForCallingInSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(isShow: Boolean) {
        // save whether showing notice for calling uid
        sharedPreferences.edit()
            .putBoolean(AppSharedPreferenceContract.NOTICE_FOR_CALLING, isShow)
            .apply()
    }
}