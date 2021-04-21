package kr.co.soogong.master.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.util.extension.addTextView3
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.EMAIL_ERROR
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PASSWORD_CONFIRMATION_ERROR
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.PASSWORD_ERROR
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.SIGNUP_SUCCESS
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel.Companion.USER_NAME_ERROR
import kr.co.soogong.master.ui.main.TabTextList
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper
import kr.co.soogong.master.uiinterface.auth.signin.SignInActivityHelper
import kr.co.soogong.master.uiinterface.auth.signup.AddressActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber
import java.text.SimpleDateFormat

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    R.layout.activity_sign_up
) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@SignUpActivity

            toolbar.setNavigationOnClickListener { super.onBackPressed() }

            with(signUpViewPager) {
                isUserInputEnabled = false
                adapter = SignUpPagerAdapter(this@SignUpActivity)
                TabLayoutMediator(signUpTabs, this) { tab, position ->
                    tab.text = getString(TabStepList[position])
                    tab.view.isClickable = false
                }.attach()
            }
        }
    }

    fun moveToNext(){
        bind {
            if(signUpViewPager.currentItem < TabCount - 1){
                signUpViewPager.currentItem = signUpViewPager.currentItem + 1
                scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_UP) }
            }
        }
    }

    fun moveToPrevious(){
        bind {
            if(signUpViewPager.currentItem > 0){
                signUpViewPager.currentItem = signUpViewPager.currentItem - 1
                scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_UP) }
            }
        }
    }





    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}