package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.domain.AppDatabase
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao
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
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}
