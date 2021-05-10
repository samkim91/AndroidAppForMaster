package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.databinding.FragmentSignUpStep5Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.BusinessTypeChipGroupHelper
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_BUSINESS_TYPE
import kr.co.soogong.master.util.extension.dp
import timber.log.Timber

@AndroidEntryPoint
class Step5Fragment : BaseFragment<FragmentSignUpStep5Binding>(
    R.layout.fragment_sign_up_step5
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private var getBusinessTypeLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            Timber.tag(TAG).d("StartActivityForResult: $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedBusinessType: BusinessType by lazy {
                    data?.getParcelableExtra(BUNDLE_BUSINESS_TYPE) ?: BusinessType(null, null)
                }
                addBusinessType(selectedBusinessType)
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            businessType.setButtonClickListener {
                getBusinessTypeLauncher.launch(Intent(CategoryActivityHelper.getIntent(
                    requireContext())))
            }

            defaultButton.setOnClickListener {
                viewModel.businessType.observe(viewLifecycleOwner, {
                    businessType.alertVisible = it.isNullOrEmpty()
                })


                if (!businessType.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun addBusinessType(businessType: BusinessType) {
        // 화면과 코드 동기화
        val tempList = viewModel.businessType.value
        tempList?.removeIf { it.category?.id == businessType.category?.id }
        tempList?.add(businessType)
        viewModel.businessType.value = tempList

        // 새로운 BusinessType List로 View를 그려준다.
        addChipGroupWithTitle(viewModel.businessType.value)
    }

    // Todo.. 굉장히 코드가 더러운데.. 2 way binding을 사용하는 방법을 찾아봐야함.
    private fun addChipGroupWithTitle(businessTypes: MutableList<BusinessType>?) {
        // 기존 View들을 삭제한다.
        binding.businessTypeContainer.removeAllViews()

        var params = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0.dp, 20.dp, 0.dp, 0.dp)

        // BusinessType List만큼 View를 그려준다.
        businessTypes?.map { businessType ->
            binding.businessTypeContainer.run {
                addView(BusinessTypeChipGroupHelper.makeEntryChipGroupWithSubTitle(
                    layoutInflater = layoutInflater,
                    item = businessType,
                    closeClickListener = { titleChipGroup, chip, projectId ->
                        val chipGroup = titleChipGroup.binding.chipGroup
                        chipGroup.removeView(chip)

                        // 화면과 코드 동기화
                        val tempList = viewModel.businessType.value
                        tempList?.find { temp ->
                            temp.category == businessType.category
                        }.let {
                            it?.projects?.removeIf { project ->
                                project.id == projectId
                            }
                        }
                        viewModel.businessType.value = tempList

                        // ChipGroup에 Chip이 하나도 없다면, 해당 ChipGroup의 Container도 삭제하는 기능
                        if (chipGroup.isEmpty()) binding.businessTypeContainer.run {
                            removeView(titleChipGroup)

                            // 화면과 코드 동기화
                            val tempList = viewModel.businessType.value
                            tempList?.remove(businessType)
                            viewModel.businessType.value = tempList
                        }
                    }
                ), params)
            }
        }
    }

    companion object {
        private const val TAG = "Step5Fragment"

        fun newInstance(): Step5Fragment {
            return Step5Fragment()
        }
    }
}