package kr.co.soogong.master.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.talk.TalkApiClient
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentMypageBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.mypage.notice.NoticeMyPageListViewHolder
import kr.co.soogong.master.ui.mypage.notice.NoticeViewHolder
import kr.co.soogong.master.uiinterface.mypage.alarm.AlarmActivityHelper
import kr.co.soogong.master.uiinterface.mypage.notice.NoticeActivityHelper
import kr.co.soogong.master.uiinterface.sign.SignMainActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    R.layout.fragment_mypage
) {
    private val viewModel: MyPageViewModel by viewModels {
        MyPageViewModelFactory(getRepository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner

            with(actionBar) {
                title.text = getString(R.string.my_page_fragment_name)
            }

            noticeList.adapter = NoticeAdapter(NoticeViewHolder.NoticeView) {
                startActivity(NoticeActivityHelper.getIntent(requireContext(), it))
            }

            setNoticeClick {
                Timber.tag(TAG).i("initLayout: Notice Button")
                startActivity(NoticeActivityHelper.getIntent(requireContext()))
            }

            setCallClick {
                Timber.tag(TAG).i("initLayout: Call Button")
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
        private const val TAG = "MyPageFragment"

        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}