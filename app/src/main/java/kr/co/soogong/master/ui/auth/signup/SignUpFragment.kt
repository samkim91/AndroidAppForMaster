package kr.co.soogong.master.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpBinding
import kr.co.soogong.master.ui.auth.AuthContainerActivity
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {
    private val viewModel: SignUpViewModel by viewModels()

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

            abpHeader.setButtonBackClickListener { (activity as AuthContainerActivity).onBackPressed() }

            with(vpContainer) {
                isUserInputEnabled = false
                adapter = SignUpPagerAdapter(this@SignUpFragment)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.indicator.observe(viewLifecycleOwner, {
            binding.vpContainer.currentItem = it

            // 본인인증, 대표자명 입력화면에서 아래와 같은 사유로 뒤로가는 버튼을 없앰.
            // 1. 본인인증 : 프래그먼트의 시작이라 이전 프래그먼트가 없음
            // 2. 대표자명 : 인증이 끝나고 휴대폰 번호 수정 가능성이 있음
            binding.bPrevious.isVisible = !(it == 0 || it == 1)
        })
    }

    companion object {
        private const val TAG = "SignUpFragment"

        fun newInstance() = SignUpFragment()
    }
}