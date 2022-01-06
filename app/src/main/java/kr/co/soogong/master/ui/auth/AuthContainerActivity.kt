package kr.co.soogong.master.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAuthContainerBinding
import kr.co.soogong.master.ui.auth.AuthContainerViewModel.Companion.SIGN_IN
import kr.co.soogong.master.ui.auth.AuthContainerViewModel.Companion.SIGN_UP
import kr.co.soogong.master.ui.auth.signin.SignInFragment
import kr.co.soogong.master.ui.auth.signmain.SignMainFragment
import kr.co.soogong.master.ui.auth.signup.SignUpFragment
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber

@AndroidEntryPoint
class AuthContainerActivity : BaseActivity<ActivityAuthContainerBinding>(
    R.layout.activity_auth_container
) {
    private val viewModel: AuthContainerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserver()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        // 초기 메인화면 띄우기
        supportFragmentManager.beginTransaction()
            .replace(binding.fcvContainer.id, SignMainFragment.newInstance())
            .commit()
    }

    private fun registerEventObserver() {
        Timber.tag(TAG).d("registerEventObserver: ")

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                SIGN_UP, SIGN_IN -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left, R.anim.slide_out_to_right
                        )
                        .replace(binding.fcvContainer.id,
                            if (action == SIGN_UP) SignUpFragment.newInstance() else SignInFragment.newInstance())
                        .addToBackStack(if (action == SIGN_UP) SIGN_UP else SIGN_IN)
                        .commit()
                }
            }
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {       // 백스택에 프래그먼트가 있으면, 그 프래그먼트를 가져온다.
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()       // 없으면, 액티비티의 백프레스
        }
    }

    companion object {
        private const val TAG = "SignActivity"
    }
}