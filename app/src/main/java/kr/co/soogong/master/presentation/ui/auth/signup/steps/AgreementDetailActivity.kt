package kr.co.soogong.master.presentation.ui.auth.signup.steps

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityAgreementDetailBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.uihelper.auth.AgreementDetailActivityHelper
import kr.co.soogong.master.presentation.uihelper.auth.AgreementDetailActivityHelper.TERMS_OF_SERVICE
import timber.log.Timber

class AgreementDetailActivity : BaseActivity<ActivityAgreementDetailBinding>(
    R.layout.activity_agreement_detail
) {
    private val documentType: String by lazy {
        AgreementDetailActivityHelper.getDocumentType(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@AgreementDetailActivity

            abHeader.title =
                if (documentType == TERMS_OF_SERVICE) getString(R.string.terms_of_service)
                else getString(R.string.private_policy)

            abHeader.setIvBackClickListener { onBackPressed() }

            tvDocument.text = when (documentType) {
                TERMS_OF_SERVICE -> resources.assets.open("TermsOfService.txt").readBytes()
                    .toString(Charsets.UTF_8)
                else -> resources.assets.open("PrivatePolicy.txt").readBytes()
                    .toString(Charsets.UTF_8)
            }
        }
    }

    companion object {
        private const val TAG = "AgreementDetailActivity"
    }
}