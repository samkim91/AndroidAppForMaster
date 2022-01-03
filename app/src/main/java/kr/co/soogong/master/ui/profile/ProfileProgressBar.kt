package kr.co.soogong.master.ui.profile

import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.model.profile.BasicInformation
import kr.co.soogong.master.data.model.profile.RequiredInformation
import kotlin.reflect.full.declaredMemberProperties

fun setRequirementInformationPercentage(
    viewModel: ProfileViewModel,
) {
    // 필수정보
    val totalRequired = RequiredInformation::class.declaredMemberProperties.size
    var filledRequired = 1      // 경위도는 제외하고 시작

    viewModel.profile.value?.requiredInformation?.let { requiredInformation ->
        with(requiredInformation) {
            if (!ownerName.isNullOrEmpty()) filledRequired++
            if (!introduction.isNullOrEmpty()) filledRequired++
            if (!shopImages.isNullOrEmpty()) filledRequired++
            if (!businessUnitInformation?.businessType.isNullOrEmpty()) filledRequired++
            if (warrantyInformation?.warrantyPeriod != null) filledRequired++
            if (!tel.isNullOrEmpty()) filledRequired++
            if (!career.isNullOrEmpty()) filledRequired++
            if (!projects.isNullOrEmpty()) filledRequired++
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