package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.Coordinate
import kr.co.soogong.master.databinding.FragmentSignUpServiceAreaBinding
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.VALIDATE_SERVICE_AREA
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.utility.NaverMapHelper
import timber.log.Timber

@AndroidEntryPoint
class ServiceAreaFragment : BaseFragment<FragmentSignUpServiceAreaBinding>(
    R.layout.fragment_sign_up_service_area
) {
    private val viewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private val naverMapHelper: NaverMapHelper by lazy {
        NaverMapHelper(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            frameLayout = binding.flMapContainer,
            coordinate = Coordinate(viewModel.latitude.value, viewModel.longitude.value),
            radius = 0,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            sdmServiceArea.dropdownAdapter = ArrayAdapter(requireContext(),
                R.layout.textview_item_dropdown,
                viewModel.serviceAreas.map { it.first })

            sdmServiceArea.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                viewModel.serviceArea.value = viewModel.serviceAreas[position]
                naverMapHelper.setLocation(
                    Coordinate(
                        viewModel.latitude.value,
                        viewModel.longitude.value
                    ),
                    viewModel.serviceArea.value?.second
                )
            }
        }
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")

        viewModel.validation.observe(viewLifecycleOwner, { validation ->
            if (validation == VALIDATE_SERVICE_AREA) {
                viewModel.serviceArea.observe(viewLifecycleOwner, {
                    binding.sdmServiceArea.dropdownError =
                        if (it == null) getString(R.string.required_field_alert) else null
                })

                if (binding.sdmServiceArea.dropdownError.isNullOrEmpty()) viewModel.moveToNext()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        naverMapHelper.setLocation(
            Coordinate(
                viewModel.latitude.value,
                viewModel.longitude.value
            ),
            viewModel.serviceArea.value?.second
        )
    }

    companion object {
        private const val TAG = "ServiceAreaFragment"

        fun newInstance() = ServiceAreaFragment()
    }
}