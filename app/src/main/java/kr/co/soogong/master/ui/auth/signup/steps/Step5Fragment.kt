package kr.co.soogong.master.ui.auth.signup.steps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.databinding.FragmentSignUpStep5Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.BusinessTypeChipGroupHelper
import kr.co.soogong.master.ui.utils.ListLiveData
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper.BUNDLE_BUSINESS_TYPE
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
                getBusinessTypeLauncher.launch(
                    Intent(
                        CategoryActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            defaultButton.setOnClickListener {
                viewModel.businessTypes.observe(viewLifecycleOwner, {
                    businessType.alertVisible = it.isNullOrEmpty()
                })


                if (!businessType.alertVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun addBusinessType(businessType: BusinessType) {
        // 새로 선택된 업종을 viewModel에 갱신해준다.
        val tempList = viewModel.businessTypes.value
        tempList?.removeIf { it.category?.id == businessType.category?.id }
        tempList?.add(businessType)
        viewModel.businessTypes.value = tempList

        // 위에서 갱신된 BusinessType List로 View를 그려준다.
        addChipGroupWithTitle(viewModel.businessTypes)
    }

    private fun addChipGroupWithTitle(businessTypes: ListLiveData<BusinessType>) {
        BusinessTypeChipGroupHelper.makeEntryChipGroupWithSubtitleForBusinessTypes(
            layoutInflater = layoutInflater,
            container = binding.businessTypeContainer,
            newBusinessTypes = businessTypes,
            viewModelBusinessTypes = viewModel.businessTypes
        )
    }

    companion object {
        private const val TAG = "Step5Fragment"

        fun newInstance(): Step5Fragment {
            return Step5Fragment()
        }
    }
}