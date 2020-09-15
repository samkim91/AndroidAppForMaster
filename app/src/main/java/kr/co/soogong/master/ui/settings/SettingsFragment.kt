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
    }

    override fun initLayout() {
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner

            setNoticeClick {
                Timber.tag(TAG).i("initLayout: Notice Button")
            }

            setPasswordClick {
                Timber.tag(TAG).i("initLayout: Password Button")
            }

            setAlarmClick {
                Timber.tag(TAG).i("initLayout: Alarm Button")
            }

            setLogoutClick {
                Timber.tag(TAG).i("initLayout: Logout Button")
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