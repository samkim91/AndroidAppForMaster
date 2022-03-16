package kr.co.soogong.master.presentation.uihelper.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import kr.co.soogong.master.domain.entity.common.PortfolioType
import kr.co.soogong.master.presentation.ui.profile.detail.portfoliolist.PortfolioListActivity

object PortfolioListActivityHelper {
    private const val EXTRA_BUNDLE_KEY = "EXTRA_BUNDLE_KEY"
    private const val EXTRA_DATA_KEY = "EXTRA_DATA_KEY"

    fun getIntent(context: Context, pageName: PortfolioType): Intent {
        return Intent(context, PortfolioListActivity::class.java).apply {
            putExtra(EXTRA_BUNDLE_KEY, bundleOf(EXTRA_DATA_KEY to pageName))
        }
    }

    fun getTypeFromSavedState(savedStateHandle: SavedStateHandle): PortfolioType {
        return savedStateHandle.get<Bundle>(EXTRA_BUNDLE_KEY)
            ?.getSerializable(EXTRA_DATA_KEY) as PortfolioType
    }
}