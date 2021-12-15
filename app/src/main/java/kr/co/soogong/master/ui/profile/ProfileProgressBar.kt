package kr.co.soogong.master.ui.profile

import android.content.Context
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.model.profile.BasicInformation
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.profile.detail.requiredinformation.EditRequiredInformationViewModel
import kr.co.soogong.master.utility.extension.toast
import kotlin.reflect.full.declaredMemberProperties

fun setRequirementInformationPercentage(
    viewModel: ProfileViewModel,
) {
    // 필수정보
    val totalRequired = RequiredInformation::class.declaredMemberProperties.size
    var filledRequired = 1      // 경위도는 제외하고 시작

    viewModel.profile.value?.requiredInformation?.let { requiredInformation ->
        with(requiredInformation) {
            if (!ownerName.isNullOrBlank()) filledRequired++
            if (!introduction.isNullOrBlank()) filledRequired++
            if (!shopImages.isNullOrEmpty()) filledRequired++
            if (!businessUnitInformation?.businessType.isNullOrBlank()) filledRequired++
            if (warrantyInformation?.warrantyPeriod != null) filledRequired++
            if (!tel.isNullOrBlank()) filledRequired++
            if (!career.isNullOrBlank()) filledRequired++
            if (!majors.isNullOrEmpty()) filledRequired++
            if (!companyAddress?.roadAddress.isNullOrEmpty()) filledRequired++
            if (serviceArea != null) filledRequired++
        }

        viewModel.percentageRequired.value =
            filledRequired.toDouble() / totalRequired.toDouble() * 100.0

        // 필수 정보를 모두 채웠고, 마스터 승인상태가 미승인인 경우, 승인요청을 자동으로 한다.
        if (viewModel.percentageRequired.value == 100.0 && viewModel.profile.value?.approvedStatus == CodeTable.NOT_APPROVED.code) {
            viewModel.requestApprove()
        }
    }

    // 기본정보
    val totalBasic = BasicInformation::class.declaredMemberProperties.size
    var filledBasic = 0

    viewModel.profile.value?.basicInformation?.let { basicInformation ->
        with(basicInformation) {
            if (freeMeasureYn != null) filledBasic++
            if (!portfolios.isNullOrEmpty()) filledBasic++
            if (!priceByProjects.isNullOrEmpty()) filledBasic++
            if (!masterConfigs.isNullOrEmpty()) filledBasic++
            if (profileImage != null) filledBasic++
            if (!email.isNullOrEmpty()) filledBasic++
        }

        viewModel.percentageBasic.value = filledBasic.toDouble() / totalBasic.toDouble() * 100.0
    }
}

fun isFulfilled(
    context: Context,
    binding: ActivityEditRequiredInformationBinding,
    viewModel: EditRequiredInformationViewModel,
): Boolean {
    with(binding) {
        with(viewModel.requiredInformation) {
            when {
                value?.introduction.isNullOrEmpty() -> {
                    introduction.parent.requestChildFocus(introduction, introduction)
                    context.toast(context.getString(R.string.required_profile_empty_introduction))
                    return false
                }
                value?.shopImages.isNullOrEmpty() -> {
                    shopImages.parent.requestChildFocus(shopImages, shopImages)
                    context.toast(context.getString(R.string.required_profile_empty_shop_images))
                    return false
                }
                value?.businessUnitInformation?.businessType.isNullOrEmpty() -> {
                    businessUnitInformation.parent.requestChildFocus(businessUnitInformation,
                        businessUnitInformation)
                    context.toast(context.getString(R.string.required_profile_empty_business_unit_information))
                    return false
                }
                value?.warrantyInformation?.warrantyPeriod == null -> {
                    warrantyInformation.parent.requestChildFocus(warrantyInformation,
                        warrantyInformation)
                    context.toast(context.getString(R.string.required_profile_empty_warranty_information))
                    return false
                }
                value?.career.isNullOrEmpty() -> {
                    career.parent.requestChildFocus(career, career)
                    context.toast(context.getString(R.string.required_profile_empty_career))
                    return false
                }
                value?.tel.isNullOrEmpty() -> {
                    phoneNumber.parent.requestChildFocus(phoneNumber, phoneNumber)
                    context.toast(context.getString(R.string.required_profile_empty_phone_number))
                    return false
                }
                value?.ownerName.isNullOrEmpty() -> {
                    ownerName.parent.requestChildFocus(ownerName, ownerName)
                    context.toast(context.getString(R.string.required_profile_empty_owner_name))
                    return false
                }
                value?.majors.isNullOrEmpty() -> {
                    major.parent.requestChildFocus(major, major)
                    context.toast(context.getString(R.string.required_profile_empty_major))
                    return false
                }
                value?.companyAddress?.roadAddress.isNullOrEmpty() -> {
                    address.parent.requestChildFocus(address, address)
                    context.toast(context.getString(R.string.required_profile_empty_address))
                    return false
                }
                value?.serviceArea == null -> {
                    serviceArea.parent.requestChildFocus(serviceArea, serviceArea)
                    context.toast(context.getString(R.string.required_profile_empty_service))
                    return false
                }
                else -> {
                    return true
                }
            }
        }
    }
}