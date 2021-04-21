package kr.co.soogong.master.ui.auth.signup.step1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep1Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpPagerAdapter
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData.Companion.getWorkExperienceList
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
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
                checkPermission()
            })

            businessRegistrationCertificate.setAdapter(
                closeClick = { position ->
                    viewModel.businessRegistrationCertificate.removeAt(position)
                    binding.businessRegistrationCertificate.replaceItems(viewModel.businessRegistrationCertificate.value)
                    binding.businessRegistrationCertificate.cameraIconVisible =
                        viewModel.businessRegistrationCertificate.getItemCount() == 0
                })

            workExperience.addDropdownClickListener {
                Timber.tag(TAG).w("Dropdown Clicked")
                val bottomDialog = BottomDialogRecyclerView("경력", getWorkExperienceList(),
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
                        businessRegistrationCertificate.cameraIconVisible = it.isNullOrEmpty()
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

                // 다음 프래그먼트로 이동..
//                if (!companyName.alertVisible && !briefIntroduction.alertVisible && !businessTypeChipGroup.alertVisible && !businessRegistrationNumber.alertVisible &&
//                    !businessRegistrationCertificate.alertVisible && !birthday.alertVisible && !businessRepresentative.alertVisible && !phoneNumber.alertVisible && !workExperience.alertVisible)
                (activity as? SignUpActivity)?.moveToNext()
            }
        }
    }

private fun registerEventObserve() {
    Timber.tag(TAG).d("registerEventObserve: ")

}

private fun checkPermission() {
    val permission = object : PermissionListener {
        override fun onPermissionGranted() {
            TedImagePicker.with(requireContext())
                .buttonBackground(R.drawable.shape_fill_green_background)
                .start { uri ->
                    viewModel.businessRegistrationCertificate.clear()
                    viewModel.businessRegistrationCertificate.add(uri)
                    binding.businessRegistrationCertificate.replaceItems(viewModel.businessRegistrationCertificate.value)
                    binding.businessRegistrationCertificate.cameraIconVisible =
                        viewModel.businessRegistrationCertificate.getItemCount() == 0
                }
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            requireContext().toast("권한이 거부되었습니다.")
        }
    }

    TedPermission.with(requireContext())
        .setPermissionListener(permission)
        .setRationaleMessage("이미지를 추가하시려면 권한을 허용해주세요.")
        .setDeniedMessage("권한을 거부하셨습니다. 앱을 사용하시려면 [앱 설정]-[권한] 에서 권한을 허용해주세요.")
        .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA)
        .check()
}

companion object {
    private const val TAG = "Step1Fragment"

    fun newInstance(): Step1Fragment {
        return Step1Fragment()
    }
}

}