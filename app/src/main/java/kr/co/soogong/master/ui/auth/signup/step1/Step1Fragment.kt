package kr.co.soogong.master.ui.auth.signup.step1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep1Binding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomsheet.BottomSheetDialogData.Companion.getWorkExperienceList
import kr.co.soogong.master.ui.dialog.bottomsheet.CustomBottomSheetDialog
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class Step1Fragment : BaseFragment<FragmentSignUpStep1Binding>(
    R.layout.fragment_sign_up_step1
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel

            lifecycleOwner = viewLifecycleOwner

            businessTypeChipGroup.addCheckedChangeListener(onCheckedChange = { group, checkedId ->
                when (checkedId) {
                    group.getChildAt(0).id, group.getChildAt(1).id -> {
                        viewModel.businessType.value =
                            if (checkedId == group.getChildAt(0).id) "개인사업자" else "법인사업자"
                        businessRegistrationGroup.visibility = View.VISIBLE
                        birthday.visibility = View.GONE
                    }
                    group.getChildAt(2).id -> {
                        viewModel.businessType.value = "프리랜서"
                        businessRegistrationGroup.visibility = View.GONE
                        birthday.visibility = View.VISIBLE
                    }
                    else -> {
                        viewModel.businessType.value = ""
                        businessRegistrationGroup.visibility = View.GONE
                        birthday.visibility = View.GONE
                    }
                }
            })

            businessRegistrationCertificate.addIconClickListener(onClick = {
                requestActivity.launch(Intent(Intent.ACTION_PICK).apply { type = "image/*" })
            })

            workExperience.addDropdownClickListener {
                Timber.tag(TAG).w("Dropdown Clicked")
                val bottomDialog = CustomBottomSheetDialog("경력", getWorkExperienceList(),
                    itemClick = { viewModel.workExperience.value = it }
                )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
            }

            nextButton.setOnClickListener {
                Timber.tag(TAG).d("nextButton Clicked: ")

                bind {
                    viewModel.companyName.observe(viewLifecycleOwner, {
                        companyName.alertVisible = it.length < 2 || it.length > 20
                    })
                    viewModel.briefIntroduction.observe(viewLifecycleOwner, {
                        briefIntroduction.alertVisible = it.length < 10
                    })
                    viewModel.businessType.observe(viewLifecycleOwner, {
                        businessTypeChipGroup.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.businessRegistrationNumber.observe(viewLifecycleOwner, {
                        businessRegistrationNumber.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.businessRegistrationCertificate.observe(viewLifecycleOwner, {
                        businessRegistrationCertificate.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.birthday.observe(viewLifecycleOwner, {
                        birthday.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.businessRepresentative.observe(viewLifecycleOwner, {
                        businessRepresentative.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.phoneNumber.observe(viewLifecycleOwner, {
                        phoneNumber.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.workExperience.observe(viewLifecycleOwner, {
                        workExperience.alertVisible = it.isNullOrEmpty()
                    })
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    private val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Todo...

        }
    }


    companion object {
        private const val TAG = "Step1Fragment"


        fun newInstance(): Step1Fragment {
            return Step1Fragment()
        }
    }

}