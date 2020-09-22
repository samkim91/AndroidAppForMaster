package kr.co.soogong.master.ui

import android.content.Context
import kr.co.soogong.master.domain.AppDatabase
import kr.co.soogong.master.domain.AppSharedPreference
import kr.co.soogong.master.domain.Repository

fun getRepository(context: Context): Repository {
    return Repository.getInstance(
        AppDatabase.getInstance(context.applicationContext).requirementDao(),
        AppDatabase.getInstance(context.applicationContext).userDao(),
        AppSharedPreference.getInstance(context.applicationContext)
    )
}