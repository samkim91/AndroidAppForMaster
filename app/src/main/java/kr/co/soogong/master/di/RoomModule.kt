package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.domain.AppDatabase
import kr.co.soogong.master.data.dao.requirement.estimation.EstimationDao
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dao.profile.ProfileDao
import kr.co.soogong.master.data.dao.requirement.RequirementDao
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
    fun provideEstimationDao(appDatabase: AppDatabase): EstimationDao {
        return appDatabase.estimationDao()
    }

    @Provides
    @Singleton
    fun provideMasterDao(appDatabase: AppDatabase): MasterDao {
        return appDatabase.masterDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(appDatabase: AppDatabase): ProfileDao {
        return appDatabase.profileDao()
    }
}
