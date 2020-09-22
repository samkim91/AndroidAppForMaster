package kr.co.soogong.master.ui.settings

import android.content.Intent
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
import kr.co.soogong.master.ui.settings.alarm.AlarmActivity
import kr.co.soogong.master.ui.settings.notice.NoticeActivity
import kr.co.soogong.master.ui.settings.password.PasswordActivity
import kr.co.soogong.master.ui.sign.SignMainActivity
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

            setNoticeClick {
                Timber.tag(TAG).i("initLayout: Notice Button")
                context?.let {
                    startActivity(Intent(it, NoticeActivity::class.java))
                }
            }

            setPasswordClick {
                Timber.tag(TAG).i("initLayout: Password Button")
                context?.let {
                    startActivity(Intent(it, PasswordActivity::class.java))
                }
            }

            setAlarmClick {
                Timber.tag(TAG).i("initLayout: Alarm Button")
                context?.let {
                    startActivity(Intent(it, AlarmActivity::class.java))
                }
            }

            setLogoutClick {
                Timber.tag(TAG).i("initLayout: Logout Button")
                viewModel.actionLogout()
            }

            setKakaoClick {
                Timber.tag(TAG).i("initLayout: Kakao Button")

                val url = TalkApiClient.instance.addChannelUrl("_mWbJxb")
                context?.let {
                    KakaoCustomTabsClient.openWithDefault(it, url)
                }
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.completeEvent.observe(viewLifecycleOwner, EventObserver {
            context?.let {
                startActivity(Intent(context, SignMainActivity::class.java))
            }
            activity?.finish()
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestUserProfile()
    }

    companion object {
        private const val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}