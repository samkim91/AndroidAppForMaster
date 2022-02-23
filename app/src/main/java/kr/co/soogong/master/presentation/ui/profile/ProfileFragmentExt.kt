package kr.co.soogong.master.presentation.ui.profile

import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.profile.BasicInformation
import kr.co.soogong.master.domain.entity.profile.RequiredInformation
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.utility.extension.dp
import kr.co.soogong.master.utility.extension.smoothScrollToView
import timber.log.Timber
import kotlin.reflect.full.declaredMemberProperties

private const val TAG = "ProfileFragmentExt"

// 프로필 필수정보 프로그래스
fun setRequiredProfileInformationProgress(
    viewModel: ProfileViewModel,
): MutableList<Pair<String, Boolean>> {
    Timber.tag(TAG).d("setRequiredProfileInformationProgress: ")

    return viewModel.profile.value?.requiredInformation!!.run {
        val totalRequired = RequiredInformation::class.declaredMemberProperties.size
        val fieldsEmptyStatus: MutableList<Pair<String, Boolean>> = mutableListOf()

        fieldsEmptyStatus.add("coordinate" to true)     // 경위도는 주소에 따라 set 되는 값으로 제외
        fieldsEmptyStatus.add("ownerName" to ownerName.isNotEmpty())
        fieldsEmptyStatus.add("introduction" to !introduction.isNullOrEmpty())
        fieldsEmptyStatus.add("shopImages" to !shopImages.isNullOrEmpty())
        fieldsEmptyStatus.add("businessUnitInformation" to !businessUnitInformation?.businessType.isNullOrEmpty())
        fieldsEmptyStatus.add("warrantyInformation" to (warrantyInformation?.warrantyPeriod != null))
        fieldsEmptyStatus.add("tel" to tel.isNotEmpty())
        fieldsEmptyStatus.add("career" to !career.isNullOrEmpty())
        fieldsEmptyStatus.add("projects" to !projects.isNullOrEmpty())
        fieldsEmptyStatus.add("companyAddress" to !companyAddress?.roadAddress.isNullOrEmpty())
        fieldsEmptyStatus.add("serviceArea" to (serviceArea != null))

        // 뷰모델에 있는 퍼센트 set
        viewModel.percentageRequired.value =
            fieldsEmptyStatus.filter { pair -> pair.second }.size.toDouble() / totalRequired.toDouble() * 100.0

        fieldsEmptyStatus
    }
}

// 마스터의 승인상태와 필수 정보들의 입력 상태를 확인
fun checkApprovedStatusAndRequiredField(
    fragmentManager: FragmentManager,
    binding: FragmentProfileBinding,
    viewModel: ProfileViewModel,
    fieldsEmptyStatus: MutableList<Pair<String, Boolean>>,
) {
    Timber.tag(TAG).d("checkApprovedStatusAndRequiredField: ")

    when {
        // 필수값이 비어 있으면, 다이얼로그를 띄워주고 "확인" 클릭 시 해당 항목에 포커싱
        (viewModel.profile.value?.approvedStatus == CodeTable.NOT_APPROVED) && (viewModel.percentageRequired.value != 100.0) -> {
            // 이전에 한번 프로필 수정하라는 다이얼로그를 띄운적이 있으면 그냥 종료.
            if (viewModel.isShownRequiredFieldDialog.value == true) return

            // 1회성으로 다이얼로그를 띄우기 위한 value set
            viewModel.setIsShownRequiredFieldDialog(true)

            showDialogForEmptyFields(fragmentManager, binding, fieldsEmptyStatus)
        }
        // 필수값이 다 차있으면, 승인요청 → 승인요청 후 필수항목 섹터의 안내 변경 (피그마 참고)
        (viewModel.profile.value?.approvedStatus == CodeTable.NOT_APPROVED) && (viewModel.percentageRequired.value == 100.0) -> {
            viewModel.requestApprove()
        }
    }
}

// 빈 프로필이 있을 경우에, 안내 다이얼로그 전시
fun showDialogForEmptyFields(
    fragmentManager: FragmentManager,
    binding: FragmentProfileBinding,
    fieldsEmptyStatus: MutableList<Pair<String, Boolean>>,
) {
    Timber.tag(TAG).d("showDialogForEmptyFields: ")

    fieldsEmptyStatus.find { pair -> !pair.second }?.run {
        DefaultDialog.newInstance(
            DialogData.notifyRequiredProfileInformation()
        ).let {
            it.setButtonsClickListener(
                onPositive = {
                    focusEmptyField(binding, this)
                },
                onNegative = {}
            )

            it.show(fragmentManager, it.tag)
        }
    }
}

// 빈 프로필 필드로 스크롤
fun focusEmptyField(binding: FragmentProfileBinding, pair: Pair<String, Boolean>) {
    Timber.tag(TAG).d("focusEmptyField: ")

    with(binding) {
        svContainer.smoothScrollToView(
            when (pair.first) {
                "ownerName" -> hbcOwnerName
                "introduction" -> hbcCompanyIntroduction
                "shopImages" -> hieShopImages
                "businessUnitInformation" -> hbcBusinessUnitInformation
                "warrantyInformation" -> hbcWarrantyInformation
                "tel" -> hbcContactInformation
                "career" -> hbcCareer
                "projects" -> hbcgProjects
                "companyAddress" -> hbcServiceAddress
                "serviceArea" -> hbmServiceArea
                else -> abHeader      // 맨 위로
            },
            10.dp
        )
    }
}

// 프로필 선택정보 프로그래스
fun setOptionalProfileInformationProgress(
    viewModel: ProfileViewModel,
) {
    Timber.tag(TAG).d("setOptionalProfileInformationProgress: ")

    val totalBasic = BasicInformation::class.declaredMemberProperties.size
    var filledBasic = 1 // freeMeasureYn 은 항상 있으니 1부터 시작

    viewModel.profile.value?.basicInformation?.let { basicInformation ->
        with(basicInformation) {
            if (portfolioCount != 0) filledBasic++
            if (priceByProjectCount != 0) filledBasic++
            if (!masterConfigs.isNullOrEmpty()) filledBasic++
            if (profileImage != null) filledBasic++
            if (!email.isNullOrEmpty()) filledBasic++
        }

        viewModel.percentageBasic.value = filledBasic.toDouble() / totalBasic.toDouble() * 100.0
    }
}