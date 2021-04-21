package kr.co.soogong.master.ui.auth.signup.step2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep2Binding
import kr.co.soogong.master.ui.auth.signup.AddressActivity
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.step1.Step1Fragment
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.uiinterface.auth.signup.AddressActivityHelper
import kr.co.soogong.master.util.extension.toast
import net.daum.mf.map.api.MapView
import timber.log.Timber

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentSignUpStep2Binding>(
    R.layout.fragment_sign_up_step2
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()

//        binding.mapView.addView(MapView(activity))
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel

            lifecycleOwner = viewLifecycleOwner

            companyAddress.setOnClick {
                addressActivityLauncher.launch(Intent(requireContext(),
                    AddressActivity::class.java))
            }

            serviceArea.addDropdownClickListener {
                Timber.tag(TAG).w("Dropdown Clicked")
                val bottomDialog =
                    BottomDialogRecyclerView("범위 선택", BottomDialogData.getServiceAreaList(),
                        itemClick = { viewModel.serviceArea.value = it }
                    )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
            }

            profileImages.addIconClickListener { checkPermission(PROFILE_IMAGES) }
            companyImages.addIconClickListener { checkPermission(COMPANY_IMAGES) }
            otherCertificates.addIconClickListener { checkPermission(OTHER_CERTIFICATES) }

            profileImages.setAdapter(
                closeClick = { position ->
                    viewModel.profileImages.removeAt(position)
                    binding.profileImages.replaceItems(viewModel.profileImages.value)
                    binding.profileImages.cameraIconVisible =
                        viewModel.profileImages.getItemCount() == 0
                })

            companyImages.setAdapter(
                closeClick = { position ->
                    viewModel.companyImages.removeAt(position)
                    binding.companyImages.replaceItems(viewModel.companyImages.value)
                })

            otherCertificates.setAdapter(
                closeClick = { position ->
                    viewModel.otherCertificates.removeAt(position)
                    binding.otherCertificates.replaceItems(viewModel.otherCertificates.value)
                })

            previousButton.setOnClickListener {
                Timber.tag(TAG).d("previousButton Clicked: ")
                (activity as? SignUpActivity)?.moveToPrevious()
            }

            nextButton.setOnClickListener {
                Timber.tag(TAG).d("nextButton Clicked: ")

                bind {
                    viewModel.companyAddress.observe(viewLifecycleOwner, {
                        companyAddress.alertVisible = it.isNullOrEmpty()
                    })
                    viewModel.serviceArea.observe(viewLifecycleOwner, {
                        alertForServiceArea.visibility = if(it.isNullOrEmpty()) View.VISIBLE else View.GONE
                    })
                }

                // 다음 프래그먼트로 이동..
                if(!companyAddress.alertVisible && alertForServiceArea.visibility == View.GONE)
                    (activity as? SignUpActivity)?.moveToNext()
            }
        }
    }

    private var addressActivityLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        Timber.tag(TAG).d("ActivityResult: $result")
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.companyAddress.value =
                result.data?.extras?.getString(AddressActivityHelper.ADDRESS).toString()
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    private fun checkPermission(clickedCamera: Int) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                when (clickedCamera) {
                    PROFILE_IMAGES -> TedImagePicker.with(requireContext())
                        .buttonBackground(R.drawable.shape_fill_green_background)
                        .start { uri ->
                            viewModel.profileImages.clear()
                            viewModel.profileImages.add(uri)
                            binding.profileImages.replaceItems(viewModel.profileImages.value)
                            binding.profileImages.cameraIconVisible =
                                viewModel.profileImages.getItemCount() == 0
                        }

                    else -> TedImagePicker.with(requireContext())
                        .buttonBackground(R.drawable.shape_fill_green_background)
                        .max(10, "10장 이하로 선택해주세요.")
                        .startMultiImage { uri ->
                            when(clickedCamera){
                                COMPANY_IMAGES -> {
                                    viewModel.companyImages.clear()
                                    viewModel.companyImages.addAll(uri)
                                    binding.companyImages.replaceItems(viewModel.companyImages.value)
                                }
                                OTHER_CERTIFICATES -> {
                                    viewModel.otherCertificates.clear()
                                    viewModel.otherCertificates.addAll(uri)
                                    binding.otherCertificates.replaceItems(viewModel.otherCertificates.value)
                                }
                            }
                        }
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
        private const val TAG = "Step2Fragment"

        private const val PROFILE_IMAGES = 100
        private const val COMPANY_IMAGES = 200
        private const val OTHER_CERTIFICATES = 300

        fun newInstance(): Step2Fragment {
            return Step2Fragment()
        }
    }

}