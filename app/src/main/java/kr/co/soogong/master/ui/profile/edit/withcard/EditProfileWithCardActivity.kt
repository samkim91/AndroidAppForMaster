package kr.co.soogong.master.ui.profile.edit.withcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditProfileWithCardBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper.PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper.PRICE_BY_PROJECTS
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
                Timber.tag(TAG).w("DefaultButtonClickListener: ")
                startActivity(Intent(EditProfileContainerActivityHelper.getIntent(this@EditProfileWithCardActivity, ADD_PORTFOLIO)))
            }

            introductionCardForPriceByProjects.addDefaultButtonClickListener {
                Timber.tag(TAG).w("DefaultButtonClickListener: ")
                startActivity(Intent(EditProfileContainerActivityHelper.getIntent(this@EditProfileWithCardActivity, ADD_PRICE_BY_PROJECTS)))
            }



        }


    }

    private fun registerEventObserve() {

    }


    companion object {
        private const val TAG = "EditProfileWithCardActivity"

    }


}