package kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditOwnerNameBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername.EditOwnerNameViewModel.Companion.GET_OWNER_NAME_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername.EditOwnerNameViewModel.Companion.SAVE_OWNER_NAME_FAILED
import kr.co.soogong.master.ui.profile.detail.requiredinformation.ownername.EditOwnerNameViewModel.Companion.SAVE_OWNER_NAME_SUCCESSFULLY
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditOwnerNameFragment :
    BaseFragment<FragmentEditOwnerNameBinding>(
        R.layout.fragment_edit_owner_name
    ) {
    private val viewModel: EditOwnerNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
        viewModel.requestOwnerName()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            defaultButton.setOnClickListener {
                viewModel.ownerName.observe(viewLifecycleOwner, {
                    ownerName.alertVisible = it.isNullOrEmpty()
                })

                if (!ownerName.alertVisible) viewModel.saveOwnerName()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { event ->
            when (event) {
                SAVE_OWNER_NAME_SUCCESSFULLY -> activity?.onBackPressed()
                SAVE_OWNER_NAME_FAILED, GET_OWNER_NAME_FAILED -> requireContext().toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditOwnerNameFragment"

        fun newInstance() = EditOwnerNameFragment()
    }
}