package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.data.source.local.preferences.NoticeDao
import kr.co.soogong.master.data.source.local.profile.MasterDao
import kr.co.soogong.master.data.source.local.requirement.RequirementDao
import kr.co.soogong.master.data.source.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideRequirementDao(appDatabase: AppDatabase): RequirementDao {
        return appDatabase.requirementDao()
    }

    @Provides
    @Singleton
    fun provideMasterDao(appDatabase: AppDatabase): MasterDao {
        return appDatabase.masterDao()
    }

    @Provides
    @Singleton
    fun provideNoticeDao(appDatabase: AppDatabase): NoticeDao {
        return appDatabase.noticeDao()
    }
}
