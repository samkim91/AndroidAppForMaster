package kr.co.soogong.master.ui.profile.detail.requiredinformation

import android.content.Context
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.utility.extension.toast

fun setLayoutForNotApprovedMaster(
    context: Context,
    binding: ActivityEditRequiredInformationBinding,
) {
    with(binding) {
        alertContainerToFillUpRequiredInformation.isVisible = true
        requiredProfileCardPercentage.isVisible = true
        defaultButton.isVisible = true
        defaultButton.isEnabled = true
        defaultButton.text = context.getString(R.string.request_confirmation)
    }
}

fun setLayoutForRequestApproveMaster(
    context: Context,
    binding: ActivityEditRequiredInformationBinding,
) {
    with(binding) {
        alertContainerToFillUpRequiredInformation.isVisible = true
        requiredProfileCardPercentage.isVisible = true
        groupSawCheckForConfirmedMaster.isVisible = false
        defaultButton.isVisible = true
        defaultButton.isEnabled = false
        defaultButton.text = context.getString(R.string.waiting_for_confirmation)
    }
}

fun setLayoutForApprovedMaster(
    binding: ActivityEditRequiredInformationBinding,
) {
    with(binding) {
        alertContainerToFillUpRequiredInformation.isVisible = false
        requiredProfileCardPercentage.isVisible = false
        groupSawCheckForConfirmedMaster.isVisible = true
        defaultButton.isVisible = false
    }
}

fun setRequirementInformationPercentage(
    context: Context,
    binding: ActivityEditRequiredInformationBinding,
    viewModel: EditRequiredInformationViewModel,
) {
    with(binding) {
        val totalCount = 10
        var filledCount = 0

        with(viewModel.requiredInformation) {
            if (!value?.introduction.isNullOrEmpty()) filledCount++
            if (!value?.shopImages.isNullOrEmpty()) filledCount++
            if (!value?.businessUnitInformation?.businessType.isNullOrEmpty()) filledCount++
            if (value?.warrantyInformation?.warrantyPeriod != null && !value?.warrantyInformation?.warrantyDescription.isNullOrEmpty()) filledCount++
            if (!value?.career.isNullOrEmpty()) filledCount++
            if (!value?.tel.isNullOrEmpty()) filledCount++
            if (!value?.ownerName.isNullOrEmpty()) filledCount++
            if (!value?.majors.isNullOrEmpty()) filledCount++
            if (!value?.companyAddress?.roadAddress.isNullOrEmpty()) filledCount++
            if (value?.serviceArea != null) filledCount++
        }

        viewModel.percentage.value = filledCount.toFloat() / totalCount.toFloat() * 100f

        requiredProfileCardPercentage.text =
            context.getString(
                R.string.percentage_of_required_information,
                viewModel.percentage.value?.toInt()
            )

        if (viewModel.percentage.value == 100.0f) {
            requiredProfileCardPercentage.setTextColor(
                context.resources.getColor(
                    R.color.c_1FC472,
                    null
                )
            )
        } else {
            requiredProfileCardPercentage.setTextColor(
                context.resources.getColor(
                    R.color.c_FF711D,
                    null
                )
            )
        }
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
                    businessUnitInformation.parent.requestChildFocus(businessUnitInformation, businessUnitInformation)
                    context.toast(context.getString(R.string.required_profile_empty_business_unit_information))
                    return false
                }
                value?.warrantyInformation?.warrantyPeriod == null || value?.warrantyInformation?.warrantyDescription.isNullOrEmpty() -> {
                    warrantyInformation.parent.requestChildFocus(warrantyInformation, warrantyInformation)
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