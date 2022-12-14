package kr.co.soogong.master.presentation.ui.preferences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentPreferencesBinding
import kr.co.soogong.master.presentation.ui.base.BaseFragment
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.ALARM
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.CUSTOMER_SERVICE
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.LOGOUT
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.NOTICE
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.REQUEST_LOGOUT
import kr.co.soogong.master.presentation.ui.preferences.PreferencesViewModel.Companion.VERSION
import kr.co.soogong.master.presentation.uihelper.auth.AuthContainerActivityHelper
import kr.co.soogong.master.presentation.uihelper.preferences.PreferencesContainerActivityHelper
import kr.co.soogong.master.presentation.uihelper.preferences.PreferencesDetailFragmentHelper.CUSTOMER_SERVICE_PAGE
import kr.co.soogong.master.presentation.uihelper.preferences.PreferencesDetailFragmentHelper.NOTICE_PAGE
import kr.co.soogong.master.presentation.uihelper.preferences.PreferencesDetailFragmentHelper.SETTING_ALARM_PAGE
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class PreferencesFragment : BaseFragment<FragmentPreferencesBinding>(
    R.layout.fragment_preferences
) {
    private val viewModel: PreferencesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
        registerEventObserve()
        checkVersion()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel

            lifecycleOwner = viewLifecycleOwner

        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        with(viewModel) {
            action.observe(viewLifecycleOwner, EventObserver { event ->
                when (event) {
                    NOTICE ->
                        startActivity(PreferencesContainerActivityHelper.getIntent(requireContext(),
                            NOTICE_PAGE))
                    ALARM ->
                        startActivity(PreferencesContainerActivityHelper.getIntent(requireContext(),
                            SETTING_ALARM_PAGE))
                    REQUEST_LOGOUT -> DefaultDialog.newInstance(
                        dialogData = DialogData.getConfirmingLogout()).let {
                        it.setButtonsClickListener(
                            onPositive = { viewModel.logout() },
                            onNegative = { }
                        )
                        it.show(parentFragmentManager, it.tag)
                    }
                    LOGOUT -> startActivity(AuthContainerActivityHelper.getIntent(requireContext()))
                    VERSION -> startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5")
                        setPackage("com.android.vending")
                    })
                    CUSTOMER_SERVICE -> startActivity(PreferencesContainerActivityHelper.getIntent(
                        requireContext(),
                        CUSTOMER_SERVICE_PAGE))
                }
            })
        }
    }

    private fun checkVersion() {
        viewModel.version.value = requireContext().packageManager
            .getPackageInfo(requireContext().packageName, 0).versionName
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.requestUserProfile()
    }

    companion object {
        private const val TAG = "PreferencesFragment"

        fun newInstance() = PreferencesFragment()
    }
}