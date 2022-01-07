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
    private val documentType: String by lazy {
        ViewPolicyActivityHelper.getDocumentType(intent)
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

            abHeader.title =
                if (documentType == TERMS_OF_SERVICE) getString(R.string.terms_of_service)
                else getString(R.string.private_policy)

            abHeader.setButtonBackClickListener { onBackPressed() }

            tvDocument.text = when (documentType) {
                TERMS_OF_SERVICE -> resources.assets.open("TermsOfService.txt").readBytes()
                    .toString(Charsets.UTF_8)
                else -> resources.assets.open("PrivatePolicy.txt").readBytes()
                    .toString(Charsets.UTF_8)
            }
        }
    }

    companion object {
        private const val TAG = "TermsOfServiceActivity"
    }
}