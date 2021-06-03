package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.user.Coordinate
import kr.co.soogong.master.databinding.FragmentSignUpStep7Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.utils.NaverMapHelper
import timber.log.Timber

@AndroidEntryPoint
class Step7Fragment : BaseFragment<FragmentSignUpStep7Binding>(
    R.layout.fragment_sign_up_step7
) {
    private val viewModel: SignUpViewModel by activityViewModels()

    private val naverMap: NaverMapHelper by lazy {
        NaverMapHelper(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            frameLayout = binding.mapView,
            coordinate = Coordinate(viewModel.latitude.value ?: 0.0, viewModel.longitude.value ?: 0.0),
            radius = 0
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
                val bottomDialog =
                    BottomDialogRecyclerView("범위 선택", BottomDialogData.getServiceAreaList(),
                        itemClick = { text, diameter ->
                            viewModel.serviceArea.value = text
                            viewModel.serviceAreaToInt.value = diameter
                            naverMap.changeServiceArea(diameter)
                        }
                    )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
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
        naverMap
    }


    companion object {
        private const val TAG = "Step7Fragment"

        fun newInstance(): Step7Fragment {
            return Step7Fragment()
        }
    }
}