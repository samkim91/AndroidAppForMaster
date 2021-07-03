package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewPolicyBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.auth.signup.ViewPolicyActivityHelper
import kr.co.soogong.master.uihelper.auth.signup.ViewPolicyActivityHelper.TERMS_OF_SERVICE
import timber.log.Timber

class ViewPolicyActivity : BaseActivity<ActivityViewPolicyBinding>(
    R.layout.activity_view_policy
) {
    private val type: String? by lazy {
        ViewPolicyActivityHelper.getType(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {

            lifecycleOwner = this@ViewPolicyActivity

            with(actionBar) {
                title.text = when(type) {
                    TERMS_OF_SERVICE -> getString(R.string.terms_of_service_action_bar)
                    else -> getString(R.string.private_policy_action_bar)
                }
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            type?.let { type ->
                policy.text = when (type) {
                    TERMS_OF_SERVICE -> getString(R.string.terms_of_service)
                    else -> getString(R.string.private_policy)
                }
            }
        }
    }

    companion object {
        private const val TAG = "TermsOfServiceActivity"
    }
}