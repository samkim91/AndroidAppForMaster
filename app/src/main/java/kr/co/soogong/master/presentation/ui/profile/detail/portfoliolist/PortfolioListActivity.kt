package kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPortfolioListBinding
import kr.co.soogong.master.domain.entity.common.ButtonTheme
import kr.co.soogong.master.domain.entity.common.PortfolioType
import kr.co.soogong.master.domain.entity.profile.portfolio.Portfolio
import kr.co.soogong.master.domain.entity.profile.portfolio.RepairPhoto
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.uihelper.common.image.ImageViewActivityHelper
import kr.co.soogong.master.presentation.uihelper.profile.EditProfileContainerActivityHelper
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
class PortfolioListActivity : BaseActivity<ActivityPortfolioListBinding>(
    R.layout.activity_portfolio_list
) {
    private val viewModel: PortfolioListViewModel by viewModels()

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
            lifecycleOwner = this@PortfolioListActivity

            with(abHeader) {
                setIvBackClickListener {
                    onBackPressed()
                }
            }

            setButtonClickListener()
            setRecyclerview()
        }
    }

    private fun setButtonClickListener() {
        binding.buttonThemeAddingItem = ButtonTheme.OutlinedPrimary

        binding.addingItemClickListener = View.OnClickListener {
            startActivity(
                EditProfileContainerActivityHelper.getIntentForEditingPortfolio(
                    this@PortfolioListActivity,
                    when (viewModel.type) {
                        PortfolioType.PORTFOLIO -> ADD_PORTFOLIO
                        PortfolioType.REPAIR_PHOTO -> ADD_REPAIR_PHOTO
                        PortfolioType.PRICE_BY_PROJECT -> ADD_PRICE_BY_PROJECTS
                    }
                )
            )
        }
    }

    private fun setRecyclerview() {
        binding.rvItems.adapter =
            PortfolioListAdapter(
                context = this,
                imageClickListener = { iPortfolio ->
                    when (iPortfolio) {
                        is Portfolio ->
                            startActivity(ImageViewActivityHelper.getIntent(this,
                                listOf(iPortfolio.beforeImage?.url!!,
                                    iPortfolio.afterImage?.url!!)))
                        is RepairPhoto ->
                            startActivity(ImageViewActivityHelper.getIntent(this,
                                iPortfolio.images?.map { it.url!! }!!))
                    }
                },
                buttonLeftClickListener = { iPortfolio ->
                    DefaultDialog.newInstance(
                        dialogData = when (viewModel.type) {
                            PortfolioType.PORTFOLIO -> DialogData.getAskingDeletePortfolio()
                            PortfolioType.REPAIR_PHOTO -> DialogData.getAskingDeleteRepairPhoto()
                            PortfolioType.PRICE_BY_PROJECT -> DialogData.getAskingDeletePriceByProject()
                        }).let {
                        it.setButtonsClickListener(
                            onPositive = { viewModel.deletePortfolio(iPortfolio.id) },
                            onNegative = { }
                        )
                        it.show(supportFragmentManager, it.tag)
                    }
                },
                buttonRightClickListener = { iPortfolio ->
                    startActivity(
                        EditProfileContainerActivityHelper.getIntentForEditingPortfolio(
                            this@PortfolioListActivity,
                            when (viewModel.type) {
                                PortfolioType.PORTFOLIO -> EDIT_PORTFOLIO
                                PortfolioType.REPAIR_PHOTO -> EDIT_REPAIR_PHOTO
                                PortfolioType.PRICE_BY_PROJECT -> EDIT_PRICE_BY_PROJECTS
                            },
                            iPortfolio
                        )
                    )
                })
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.initList()
    }

    companion object {
        private const val TAG = "EditProfileWithCardActivity"

    }
}