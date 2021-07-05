package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import javax.inject.Inject

@Reusable
class GetMasterIdFromSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): Int {
        return sharedPreferences.getInt(AppSharedPreferenceContract.MASTER_ID, 0)
    }
}