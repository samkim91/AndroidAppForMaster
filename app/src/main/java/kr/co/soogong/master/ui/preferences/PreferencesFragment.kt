package kr.co.soogong.master.ui.preferences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ShapeTheme
import kr.co.soogong.master.databinding.FragmentPreferencesBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.preferences.PreferencesViewModel.Companion.ALARM
import kr.co.soogong.master.ui.preferences.PreferencesViewModel.Companion.LOGOUT
import kr.co.soogong.master.ui.preferences.PreferencesViewModel.Companion.NOTICE
import kr.co.soogong.master.ui.preferences.PreferencesViewModel.Companion.REQUEST_LOGOUT
import kr.co.soogong.master.ui.preferences.PreferencesViewModel.Companion.VERSION
import kr.co.soogong.master.uihelper.auth.SignMainActivityHelper
import kr.co.soogong.master.uihelper.preferences.AlarmActivityHelper
import kr.co.soogong.master.uihelper.preferences.PreferencesContainerActivityHelper
import kr.co.soogong.master.uihelper.preferences.PreferencesDetailFragmentHelper
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
            shapeThemeLabelApprovedStatus = ShapeTheme.Circle

        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        with(viewModel) {
            action.observe(viewLifecycleOwner, EventObserver { event ->
                when (event) {
                    NOTICE -> {
                        startActivity(PreferencesContainerActivityHelper.getIntent(requireContext(),
                            PreferencesDetailFragmentHelper.NOTICE_PAGE))
                    }

                    ALARM -> {
                        startActivity(AlarmActivityHelper.getIntent(requireContext()))
                    }
                    REQUEST_LOGOUT -> {
                        CustomDialog.newInstance(
                            dialogData = DialogData.getConfirmingLogout(requireContext())).let {
                            it.setButtonsClickListener(
                                onPositive = { viewModel.logout() },
                                onNegative = { }
                            )
                            it.show(parentFragmentManager, it.tag)
                        }
                    }
                    LOGOUT -> {
                        startActivity(SignMainActivityHelper.getIntent(requireContext()))
                    }
                    VERSION -> {
                        startActivity(Intent(Intent.ACTION_VIEW)
                            .apply {
                                data =
                                    Uri.parse("https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5")
                                setPackage("com.android.vending")
                            }
                        )
                    }
//                    KAKAO -> {
//                        val url = TalkApiClient.instance.addChannelUrl("_xgxkbJxb")
//                        Timber.tag(TAG).d("MyPageViewModel.KAKAO clicked: $url")
//                        KakaoCustomTabsClient.openWithDefault(requireContext(), url)
//                    }
                    //                    MyPageViewModel.ACCOUNT -> {
//                        startActivity(AccountActivityHelper.getIntent(requireContext()))
//                    }

//                    MyPageViewModel.CALL -> {
//                        startActivity(
//                            Intent(
//                                Intent.ACTION_DIAL,
//                                Uri.parse("tel:16444095")
//                            )
//                        )
//                    }
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