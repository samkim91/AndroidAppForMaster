package kr.co.soogong.master.network

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Reusable
    fun provideAlarmService(retrofit: Retrofit): AlarmService {
        return AlarmService(retrofit)
    }

    @Provides
    @Reusable
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return AuthService(retrofit)
    }

    @Provides
    @Reusable
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return CategoryService(retrofit)
    }

    @Provides
    @Reusable
    fun provideEstimationsService(retrofit: Retrofit): EstimationsService {
        return EstimationsService(retrofit)
    }

    @Provides
    @Reusable
    fun provideMyPageService(retrofit: Retrofit): MyPageService {
        return MyPageService(retrofit)
    }

    @Provides
    @Reusable
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return ProfileService(retrofit)
    }
}