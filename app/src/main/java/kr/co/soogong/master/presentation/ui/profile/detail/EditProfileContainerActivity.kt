package kr.co.soogong.master.presentation.ui.profile.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.profile.portfolio.PortfolioDto
import kr.co.soogong.master.databinding.ActivityEditProfileContainerBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_SUCCESS
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.ADD_REPAIR_PHOTO
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_REPAIR_PHOTO
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EditProfileContainerActivity : BaseActivity<ActivityEditProfileContainerBinding>(
    R.layout.activity_edit_profile_container
) {
    private val viewModel: EditProfileContainerViewModel by viewModels()

    private val pageName: String by lazy {
        EditProfileContainerActivityHelper.getPageName(intent)
    }

    private val portfolioDto: PortfolioDto? by lazy {
        EditProfileContainerActivityHelper.getPortfolio(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerObservers()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: $pageName")

        bind {
            buttonThemeSave = ButtonTheme.Primary

            with(abHeader) {
                title = pageName
                setIvBackClickListener { super.onBackPressed() }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fcv_container,
                if (pageName == ADD_PORTFOLIO || pageName == EDIT_PORTFOLIO || pageName == ADD_REPAIR_PHOTO || pageName == EDIT_REPAIR_PHOTO || pageName == ADD_PRICE_BY_PROJECTS || pageName == EDIT_PRICE_BY_PROJECTS)
                    EditProfileContainerFragmentHelper.getFragmentWithPortfolio(pageName,
                        portfolioDto)
                else EditProfileContainerFragmentHelper.getFragment(pageName)
            ).commit()
    }

    private fun registerObservers() {
        Timber.tag(TAG).d("registerObservers: ")

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                REQUEST_SUCCESS -> this.finish()
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    fun setSaveButtonClickListener(onClick: () -> Unit) {
        binding.bSave.setOnClickListener { onClick() }
    }

    fun setSaveButtonEnabled(boolean: Boolean) {
        Timber.tag(TAG).d("setSaveButtonEnabled: $boolean")
        binding.bSave.isEnabled = boolean
    }

    companion object {
        private const val TAG = "EditProfileDetailActivity"

    }
}