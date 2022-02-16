package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.data.datasource.network.auth.AuthService
import kr.co.soogong.master.data.datasource.network.home.HomeService
import kr.co.soogong.master.data.datasource.network.common.major.MajorService
import kr.co.soogong.master.data.datasource.network.preferences.PreferencesService
import kr.co.soogong.master.data.datasource.network.profile.ProfileService
import kr.co.soogong.master.data.datasource.network.requirement.RequirementService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Reusable
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return AuthService(retrofit)
    }

    @Provides
    @Reusable
    fun provideMajorService(retrofit: Retrofit): MajorService {
        return MajorService(retrofit)
    }

    @Provides
    @Reusable
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return HomeService(retrofit)
    }

    @Provides
    @Reusable
    fun provideRequirementService(retrofit: Retrofit): RequirementService {
        return RequirementService(retrofit)
    }

    @Provides
    @Reusable
    fun provideMyPageService(retrofit: Retrofit): PreferencesService {
        return PreferencesService(retrofit)
    }

    @Provides
    @Reusable
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return ProfileService(retrofit)
    }
}