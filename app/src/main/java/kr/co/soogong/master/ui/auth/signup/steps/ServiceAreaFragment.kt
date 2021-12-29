package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.Coordinate
import kr.co.soogong.master.databinding.FragmentSignUpServiceAreaBinding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogBundle
import kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView.BottomSheetDialogRecyclerView
import kr.co.soogong.master.utility.NaverMapHelper
import kr.co.soogong.master.utility.PermissionHelper
import timber.log.Timber

@AndroidEntryPoint
class ServiceAreaFragment : BaseFragment<FragmentSignUpServiceAreaBinding>(
    R.layout.fragment_sign_up_service_area
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private val naverMapHelper: NaverMapHelper by lazy {
        NaverMapHelper(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            frameLayout = binding.mapView,
            coordinate = Coordinate(viewModel.latitude.value, viewModel.longitude.value),
            radius = 0,
        )
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

            serviceArea.addDropdownClickListener {
                PermissionHelper.checkLocationPermission(
                    context = requireContext(),
                    onGranted = {
                        BottomSheetDialogRecyclerView.newInstance(
                            sheetDialogBundle = BottomSheetDialogBundle.getServiceAreaBundle()
                        ).let {
                            it.setItemClickListener { dialogItem ->
                                viewModel.serviceArea.value = dialogItem.key
                                viewModel.serviceAreaToInt.value = dialogItem.value
                                naverMapHelper.setLocation(
                                    Coordinate(
                                        viewModel.latitude.value,
                                        viewModel.longitude.value
                                    ), dialogItem.value
                                )
                            }
                            it.show(parentFragmentManager, it.tag)
                        }
                    },
                    onDenied = { }
                )
            }

            defaultButton.setOnClickListener {
                viewModel.serviceArea.observe(viewLifecycleOwner, {
                    alertServiceAreaRequired.isVisible = it.isNullOrEmpty()
                })

                if (!alertServiceAreaRequired.isVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        naverMapHelper
    }

    companion object {
        private const val TAG = "ServiceAreaFragment"

        fun newInstance(): ServiceAreaFragment {
            return ServiceAreaFragment()
        }
    }
}