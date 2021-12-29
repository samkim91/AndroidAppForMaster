package kr.co.soogong.master.ui.preferences.detail.customerservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.talk.TalkApiClient
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.FragmentCustomerServiceBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.preferences.detail.customerservice.CustomerServiceViewModel.Companion.VIA_CALL
import kr.co.soogong.master.ui.preferences.detail.customerservice.CustomerServiceViewModel.Companion.VIA_KAKAO
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class CustomerServiceFragment : BaseFragment<FragmentCustomerServiceBinding>(
    R.layout.fragment_customer_service
) {
    private val viewModel: CustomerServiceViewModel by viewModels()

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
            buttonThemeCall = ButtonTheme.Primary
            buttonThemeKakao = ButtonTheme.Kakao

        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.action.observe(viewLifecycleOwner, EventObserver { action ->
            when (action) {
                VIA_KAKAO -> KakaoCustomTabsClient.openWithDefault(requireContext(),
                    TalkApiClient.instance.addChannelUrl("_xgxkbJxb"))
                VIA_CALL -> startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:16444095")))
            }
        })
    }

    companion object {
        private const val TAG = "AlarmFragment"

        fun newInstance() = CustomerServiceFragment()
    }
}