package kr.co.soogong.master.domain.entity.auth

import com.google.gson.annotations.SerializedName
import kr.co.soogong.master.data.entity.auth.MasterSignUpDto

data class MasterSignUp(
    val uid: String,
    val ownerName: String,
    val tel: String,
    val privatePolicy: Boolean,
    val marketingPush: Boolean,
    val pushAtNight: Boolean,
    val repairInPerson: Boolean,
) {
    companion object {
        fun fromDto(masterSignUpDto: MasterSignUpDto): MasterSignUp =
            MasterSignUp(
                uid = masterSignUpDto.uid,
                ownerName = masterSignUpDto.ownerName,
                tel = masterSignUpDto.tel,
                privatePolicy = masterSignUpDto.privatePolicy,
                marketingPush = masterSignUpDto.marketingPush,
                pushAtNight = masterSignUpDto.pushAtNight,
                repairInPerson = masterSignUpDto.repairInPerson,
            )
    }
}