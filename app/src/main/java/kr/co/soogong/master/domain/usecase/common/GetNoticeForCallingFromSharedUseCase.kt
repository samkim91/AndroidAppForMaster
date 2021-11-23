package kr.co.soogong.master.domain.usecase.common

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class GetNoticeForCallingFromSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke(): Boolean =
        sharedPreferences.getBoolean(AppSharedPreferenceContract.NOTICE_FOR_CALLING, true)
}