package kr.co.soogong.master.ui.profile.edit

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditProfileWithCardBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.profile.edit.EditProfileDetailActivity.Companion.EDIT_PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileDetailActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class EditProfileWithCardActivity : BaseActivity<ActivityEditProfileWithCardBinding>(
    R.layout.activity_edit_profile_with_card
) {
    private val pageName: String by lazy {
        EditProfileWithCardActivityHelper.getPageName(intent)
    }

    private val viewModel: EditProfileWithCardViewModel by viewModels()

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
            lifecycleOwner = this@EditProfileWithCardActivity

            with(actionBar) {
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            when (pageName) {
                PORTFOLIO -> {
                    actionBar.title.text = PORTFOLIO
                    introductionCardForPortfolio.visibility = View.VISIBLE
                    introductionCardForPriceByProjects.visibility = View.GONE
                }
                PRICE_BY_PROJECTS -> {
                    actionBar.title.text = PRICE_BY_PROJECTS
                    introductionCardForPriceByProjects.visibility = View.VISIBLE
                    introductionCardForPortfolio.visibility = View.GONE
                }
            }

            introductionCardForPortfolio.addDefaultButtonClickListener {
                Timber.tag(TAG).w("addDefaultButtonClickListener: ")
                startActivity(Intent(EditProfileDetailActivityHelper.getIntent(this@EditProfileWithCardActivity, EDIT_PORTFOLIO)))
            }


        }


    }

    private fun registerEventObserve() {

    }


    companion object {
        private const val TAG = "EditProfileWithCardActivity"
        const val PORTFOLIO = "포트폴리오 편집하기"
        const val PRICE_BY_PROJECTS = "시공 종류별 가격 편집하기"
    }


}