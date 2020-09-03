package kr.co.soogong.master.ui.util

import android.content.Context
import kr.co.soogong.master.domain.AppDatabase
import kr.co.soogong.master.domain.Repository

fun getRepository(context: Context): Repository {
    return Repository.getInstance(
        AppDatabase.getInstance(context.applicationContext).requirementDao()
    )
}