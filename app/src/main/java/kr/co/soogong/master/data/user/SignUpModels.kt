package kr.co.soogong.master.data.user

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpModel(
    val step1Model: Step1Model,


    ) : Parcelable


@Parcelize
data class Step1Model(
    val companyName: String,

    val briefIntroduction: String,

    val businessType: String,

    val businessRegistrationNumber: String?,

    val businessRegistrationCertificate: String?,

    val birthday: String?,

    val businessRepresentative: String,

    val phoneNumber: String,

    val workExperience: String
) : Parcelable
