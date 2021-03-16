package kr.co.soogong.master.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.domain.AppDatabase
import kr.co.soogong.master.domain.AppSharedPreference
import kr.co.soogong.master.domain.Repository
import kr.co.soogong.master.domain.estimation.EstimationDao
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "soogong-master.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

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

    @Provides
    @Singleton
    fun provideAppSharedPreference(@ApplicationContext context: Context): AppSharedPreference {
        return AppSharedPreference(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun getRepository(
        estimationDao: EstimationDao,
        requirementDao: RequirementDao,
        userDao: UserDao,
        appSharedPreference: AppSharedPreference
    ): Repository {
        return Repository(
            estimationDao,
            requirementDao,
            userDao,
            appSharedPreference
        )
    }
}
