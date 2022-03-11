package kr.co.soogong.master.presentation.uihelper.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.presentation.ui.common.MajorActivity

object MajorActivityHelper {
    private const val EXTRA_KEY = "EXTRA_KEY"
    private const val MAX_NUMBER = "MAX_NUMBER"

    private const val BUNDLE_MAJOR = "BUNDLE_MAJOR"
    private const val EXTRA_PROJECT = "EXTRA_PROJECT"

    fun getIntent(context: Context, maxNumber: Int = 0): Intent {
        return Intent(context, MajorActivity::class.java).apply {
            putExtra(EXTRA_KEY, bundleOf(MAX_NUMBER to maxNumber))
        }
    }

    fun getMaxNumberFromIntent(intent: Intent) =
        intent.getBundleExtra(EXTRA_KEY)?.getInt(MAX_NUMBER) ?: 0

    fun getProjectsFromIntent(intent: Intent) =
        intent.getBundleExtra(BUNDLE_MAJOR)?.getParcelableArrayList<Project>(EXTRA_PROJECT)

    fun getIntentIncludingProjects(projects: MutableList<Project>) = Intent().apply {
        putExtra(BUNDLE_MAJOR, Bundle().apply {
            putParcelableArrayList(
                EXTRA_PROJECT, ArrayList<Parcelable>(projects)
            )
        })
    }
}