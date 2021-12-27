package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.network.auth.AuthService
import kr.co.soogong.master.network.home.HomeService
import kr.co.soogong.master.network.major.MajorService
import kr.co.soogong.master.network.mypage.MyPageService
import kr.co.soogong.master.network.profile.ProfileService
import kr.co.soogong.master.network.requirement.RequirementService
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
    fun provideCategoryService(retrofit: Retrofit): MajorService {
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
    fun provideMyPageService(retrofit: Retrofit): MyPageService {
        return MyPageService(retrofit)
    }

    @Provides
    @Reusable
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return ProfileService(retrofit)
    }
}