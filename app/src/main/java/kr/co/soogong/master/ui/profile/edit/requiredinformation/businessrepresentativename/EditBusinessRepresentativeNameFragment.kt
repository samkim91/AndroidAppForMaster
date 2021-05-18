package kr.co.soogong.master.ui.profile.edit.requiredinformation.businessrepresentativename

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditBusinessRepresentativeNameBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessrepresentativename.EditBusinessRepresentativeNameViewModel.Companion.SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED
import kr.co.soogong.master.ui.profile.edit.requiredinformation.businessrepresentativename.EditBusinessRepresentativeNameViewModel.Companion.SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditBusinessRepresentativeNameFragment :
    BaseFragment<FragmentEditBusinessRepresentativeNameBinding>(
        R.layout.fragment_edit_business_representative_name
    ) {
    private val viewModel: EditBusinessRepresentativeNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.getBusinessRepresentative()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.businessRepresentativeName.observe(viewLifecycleOwner, {
                    businessRepresentativeName.alertVisible = it.isNullOrEmpty()
                })

                if (!businessRepresentativeName.alertVisible) viewModel.saveBusinessRepresentative()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_BUSINESS_REPRESENTATIVE_NAME_SUCCESSFULLY -> {
                    activity?.onBackPressed()
                }
                SAVE_BUSINESS_REPRESENTATIVE_NAME_FAILED -> {
                    requireContext().toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    companion object {
        private const val TAG = "EditBusinessRepresentativeNameFragment"

        fun newInstance() = EditBusinessRepresentativeNameFragment()
    }
}