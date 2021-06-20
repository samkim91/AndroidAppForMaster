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
        // TODO: 2021/06/16 개발 끝나면 삭제
        return sharedPreferences.getInt(AppSharedPreferenceContract.MASTER_ID, 0)
    }
}