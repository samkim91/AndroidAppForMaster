package kr.co.soogong.master.ui.auth.signup

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.utils.SignUpProgressHelper
import timber.log.Timber

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
            vm = viewModel
            lifecycleOwner = this@SignUpActivity

            viewModel.indicator.value = 1

            with(actionBar) {
                title.text = ""
                backButton.setOnClickListener {
                    onBackPressed()
                }
            }

            progressPercentIndicator.text = SignUpProgressHelper(signUpViewPager.currentItem)

            with(signUpViewPager) {
                isUserInputEnabled = false
                adapter = SignUpPagerAdapter(this@SignUpActivity)
            }
        }
    }

    fun moveToNext() {
        Timber.tag(TAG).d("moveToNext: ")
        bind {
            if (signUpViewPager.currentItem < TabCount - 1) {
                signUpViewPager.currentItem++
                viewModel.indicator.value = viewModel.indicator.value?.plus(1)
                progressPercentIndicator.text = SignUpProgressHelper(signUpViewPager.currentItem)
            }
        }
    }

    fun moveToPrevious() {
        Timber.tag(TAG).d("moveToPrevious: ")
        bind {
            if (signUpViewPager.currentItem > 0) {
                signUpViewPager.currentItem--
                viewModel.indicator.value = viewModel.indicator.value?.minus(1)
                progressPercentIndicator.text = SignUpProgressHelper(signUpViewPager.currentItem)
            }
        }
    }

    override fun onBackPressed() {
        Timber.tag(TAG).d("onBackPressed: ")
        val dialog = CustomDialog(DialogData.cancelSignUpDialogData(this@SignUpActivity),
            yesClick = { },
            noClick = { super.onBackPressed() })

        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}