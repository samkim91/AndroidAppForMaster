package kr.co.soogong.master.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.talk.TalkApiClient
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentMypageBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.mypage.notice.NoticeInMyPageViewHolder
import kr.co.soogong.master.uihelper.auth.SignMainActivityHelper
import kr.co.soogong.master.uihelper.mypage.account.AccountActivityHelper
import kr.co.soogong.master.uihelper.mypage.alarm.AlarmActivityHelper
import kr.co.soogong.master.uihelper.mypage.notice.NoticeActivityHelper
import kr.co.soogong.master.uihelper.mypage.notice.detail.NoticeDetailActivityHelper
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    R.layout.fragment_mypage
) {
    private val viewModel: MyPageViewModel by viewModels()

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

            noticeList.adapter = NoticeAdapter(NoticeInMyPageViewHolder.NoticeMypageListView) {
                startActivity(NoticeDetailActivityHelper.getIntent(requireContext(), it))
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        with(viewModel) {
            action.observe(viewLifecycleOwner, EventObserver { event ->
                when (event) {
                    MyPageViewModel.ACCOUNT -> {
                        startActivity(AccountActivityHelper.getIntent(requireContext()))
                    }
                    MyPageViewModel.ALARM -> {
                        startActivity(AlarmActivityHelper.getIntent(requireContext()))
                    }
                    MyPageViewModel.CALL -> {
                        startActivity(
                            Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel:16444095")
                            )
                        )
                    }
                    MyPageViewModel.KAKAO -> {
                        val url = TalkApiClient.instance.addChannelUrl("_xgxkbJxb")
                        Timber.tag(TAG).d("MyPageViewModel.KAKAO clicked: $url")
                        KakaoCustomTabsClient.openWithDefault(requireContext(), url)
                    }
                    MyPageViewModel.NOTICE -> {
                        startActivity(NoticeActivityHelper.getIntent(requireContext()))
                    }
                    MyPageViewModel.LOGOUT -> {
                        startActivity(SignMainActivityHelper.getIntent(requireContext()))
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume: ")
        viewModel.initialize()
    }

    companion object {
        private const val TAG = "MyPageFragment"

        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}