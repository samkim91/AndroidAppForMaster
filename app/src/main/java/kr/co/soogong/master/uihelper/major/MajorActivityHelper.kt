package kr.co.soogong.master.uihelper.major

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import kr.co.soogong.master.data.dto.profile.ProjectDto
import kr.co.soogong.master.ui.major.MajorActivity

object MajorActivityHelper {
    const val BUNDLE_MAJOR = "BUNDLE_MAJOR"
    const val EXTRA_PROJECT = "EXTRA_PROJECT"

    fun getIntent(context: Context): Intent {
        return Intent(context, MajorActivity::class.java)
    }

    fun getProjectsFromIntent(intent: Intent) = intent.getBundleExtra(BUNDLE_MAJOR)?.getParcelableArrayList<ProjectDto>(EXTRA_PROJECT)

    fun getIntentIncludingProjects(projects: MutableList<ProjectDto>) = Intent().apply {
        putExtra(BUNDLE_MAJOR, Bundle().apply {
            putParcelableArrayList(
                EXTRA_PROJECT, ArrayList<Parcelable>(projects)
            )
        })
    }
}