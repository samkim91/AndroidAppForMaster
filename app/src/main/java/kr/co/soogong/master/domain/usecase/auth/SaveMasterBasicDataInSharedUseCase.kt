package kr.co.soogong.master.domain.usecase.auth

import android.content.SharedPreferences
import dagger.Reusable
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import kr.co.soogong.master.data.dto.profile.MasterDto
import javax.inject.Inject

@Reusable
class SaveMasterBasicDataInSharedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(masterDto: MasterDto) {
        // save master id
        sharedPreferences.edit()
            .putInt(AppSharedPreferenceContract.MASTER_ID, masterDto.id!!)

        // save master uid
        sharedPreferences.edit()
            .putString(AppSharedPreferenceContract.MASTER_UID, masterDto.uid!!)
            .apply()

        // save master approval
        sharedPreferences.edit()
            .putBoolean(
                AppSharedPreferenceContract.IS_APPROVED,
                !(masterDto.subscriptionPlan == "NotApproved" || masterDto.subscriptionPlan == "RequestedApprove")
            )
            .apply()
    }
}