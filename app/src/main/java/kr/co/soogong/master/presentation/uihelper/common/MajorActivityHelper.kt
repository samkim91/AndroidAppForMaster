package kr.co.soogong.master.presentation.uihelper.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.presentation.ui.common.MajorActivity

object MajorActivityHelper {
    private const val BUNDLE_MAJOR = "BUNDLE_MAJOR"
    private const val EXTRA_PROJECT = "EXTRA_PROJECT"

    fun getIntent(context: Context): Intent {
        return Intent(context, MajorActivity::class.java)
    }

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