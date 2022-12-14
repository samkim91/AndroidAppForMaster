package kr.co.soogong.master.presentation.ui.profile.detail.email

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditEmailBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerActivity
import kr.co.soogong.master.presentation.ui.profile.detail.EditProfileContainerViewModel
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.isValidDomain
import kr.co.soogong.master.utility.extension.isValidLocalPart
import timber.log.Timber

@AndroidEntryPoint
class EditEmailFragment : BaseFragment<FragmentEditEmailBinding>(
    R.layout.fragment_edit_email
) {

    private val viewModel: EditEmailViewModel by viewModels()
    private val editProfileContainerViewModel: EditProfileContainerViewModel by activityViewModels()

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

            sefEmail.dropdownAdapter = ArrayAdapter(requireContext(),
                R.layout.textview_item_dropdown,
                viewModel.domains.map { it.first })

            sefEmail.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                if (position == viewModel.domains.last().second) {      // 직접 입력 시 텍스트 지움 및 수정 가능
                    viewModel.domain.value = Pair("", viewModel.domains.last().second)
                    sefEmail.dropdownInputType = EditorInfo.TYPE_CLASS_TEXT
                } else {        // 직접 입력이 아닐 시 수정 불가능
                    viewModel.domain.value = viewModel.domains[position]
                    sefEmail.dropdownInputType = EditorInfo.TYPE_NULL
                }
            }

            (activity as EditProfileContainerActivity).setSaveButtonClickListener {
                // 도메인이 직접 입력일 시, 해당 값을 viewModel 에 set
                if (viewModel.domain.value?.second == viewModel.domains.last().second) {
                    viewModel.domain.value = Pair(sefEmail.autoCompleteTextView.text.toString(),
                        viewModel.domains.last().second)
                }

                viewModel.localPart.observe(viewLifecycleOwner) {
                    sefEmail.error =
                        if (!it.isValidLocalPart()) getString(R.string.invalid_email_format) else null
                }

                viewModel.domain.observe(viewLifecycleOwner) {
                    sefEmail.dropdownError =
                        if (!it.first.isValidDomain()) getString(R.string.invalid_email_format) else null
                }

                if (sefEmail.error.isNullOrEmpty() && sefEmail.dropdownError.isNullOrEmpty()) viewModel.saveEmailAddress()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> editProfileContainerViewModel.setAction(REQUEST_SUCCESS)
            }
        })
    }

    companion object {
        private const val TAG = "EditEmailFragment"

        fun newInstance() = EditEmailFragment()
    }
}