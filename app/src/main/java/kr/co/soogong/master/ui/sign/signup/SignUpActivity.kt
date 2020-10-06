package kr.co.soogong.master.ui.sign.signup

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.ext.createLabelToggle
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.category.CategoryFragment
import timber.log.Timber

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    R.layout.activity_sign_up
) {
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@SignUpActivity

            with(actionBar) {
                title.text = "가입신청"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            viewModel.list.observe(this@SignUpActivity, { list ->
                if (!list.isNullOrEmpty()) {
                    category.removeAllViews()

                    for (item in list) {
                        val view = createLabelToggle(
                            this@SignUpActivity,
                            item,
                            checked = true,
                            clickable = false
                        )
                        category.addView(view)
                    }
                }
            })

            setCategorySelectClick {
                CategoryFragment.newInstance(
                    list = viewModel.list.value ?: emptyList(),
                    listener = {
                        viewModel.sendList(it)
                    }).show(supportFragmentManager, CategoryFragment.TAG)
            }

            setFindLocationClick {

            }

            setSignUpClick {

            }
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}