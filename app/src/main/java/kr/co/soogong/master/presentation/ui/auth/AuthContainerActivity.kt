package kr.co.soogong.master.presentation.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAuthContainerBinding
import kr.co.soogong.master.presentation.ui.auth.AuthContainerViewModel.Companion.SIGN_UP
import kr.co.soogong.master.presentation.ui.auth.signin.SignInFragment
import kr.co.soogong.master.presentation.ui.auth.signup.SignUpFragment
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
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

        viewModel.action.observe(this, EventObserver { fragmentTag ->
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left, R.anim.slide_out_to_right
                )
                .replace(
                    binding.fcvContainer.id,
                    if (fragmentTag == SIGN_UP) SignUpFragment.newInstance() else SignInFragment.newInstance(),
                    fragmentTag
                )
                .addToBackStack(fragmentTag)
                .commit()
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {       // 백스택에 프래그먼트가 있으면, 자식 프래그먼트에 있다는 의미
            // 회원가입 화면에서 뒤로가기를 눌렀을 때
            supportFragmentManager.findFragmentByTag(SIGN_UP)?.let { fragment ->
                if (fragment.isVisible) {
                    showDialogForKeepingSignUp()
                    return
                }
            }

            // 로그인 화면에서 뒤로가기를 눌렀을 때
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()       // 백스택이 없으면 SignMainFragment 라는 의미이고, 앱을 종료
        }
    }

    private fun showDialogForKeepingSignUp() {
        DefaultDialog.newInstance(DialogData.getConfirmingQuitSignUp())
            .let {
                it.setButtonsClickListener(
                    onPositive = {},
                    onNegative = { supportFragmentManager.popBackStack() }
                )

                it.show(supportFragmentManager, it.tag)
            }
    }

    companion object {
        private const val TAG = "AuthContainerActivity"
    }
}