package kr.co.soogong.master.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.talk.TalkApiClient
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSettingsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.uiinterface.settings.alarm.AlarmActivityHelper
import kr.co.soogong.master.uiinterface.settings.notice.NoticeActivityHelper
import kr.co.soogong.master.uiinterface.settings.password.PasswordActivityHelper
import kr.co.soogong.master.uiinterface.sign.SignMainActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    R.layout.fragment_settings
) {
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(getRepository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner

            noticeList.adapter = SettingsNoticeAdapter()

            setNoticeClick {
                Timber.tag(TAG).i("initLayout: Notice Button")
                startActivity(NoticeActivityHelper.getIntent(requireContext()))
            }

            setPasswordClick {
                Timber.tag(TAG).i("initLayout: Password Button")
                startActivity(PasswordActivityHelper.getIntent(requireContext()))
            }

            setAlarmClick {
                Timber.tag(TAG).i("initLayout: Alarm Button")
                startActivity(AlarmActivityHelper.getIntent(requireContext()))
            }

            setLogoutClick {
                Timber.tag(TAG).i("initLayout: Logout Button")
                viewModel.actionLogout()
            }

            setKakaoClick {
                Timber.tag(TAG).i("initLayout: Kakao Button")

                val url = TalkApiClient.instance.addChannelUrl("_mWbJxb")
                KakaoCustomTabsClient.openWithDefault(requireContext(), url)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.completeEvent.observe(viewLifecycleOwner, EventObserver {
            startActivity(SignMainActivityHelper.getIntent(requireContext()))
            activity?.finish()
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestUserProfile()
        viewModel.getNoticeList()
    }

    companion object {
        private const val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}