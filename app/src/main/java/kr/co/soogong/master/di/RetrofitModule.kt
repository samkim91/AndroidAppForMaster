package kr.co.soogong.master.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.data.source.network.auth.AuthService
import kr.co.soogong.master.data.source.network.common.major.MajorService
import kr.co.soogong.master.data.source.network.preferences.PreferencesService
import kr.co.soogong.master.data.source.network.profile.ProfileService
import kr.co.soogong.master.data.source.network.requirement.RequirementService
import kr.co.soogong.master.data.source.network.requirement.estimation.EstimationService
import kr.co.soogong.master.data.source.network.requirement.repair.RepairService
import kr.co.soogong.master.data.source.network.requirement.review.ReviewService
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
    fun provideRequirementService(retrofit: Retrofit): RequirementService {
        return RequirementService(retrofit)
    }

    @Provides
    @Reusable
    fun provideEstimationService(retrofit: Retrofit): EstimationService {
        return EstimationService(retrofit)
    }

    @Provides
    @Reusable
    fun provideRepairService(retrofit: Retrofit): RepairService {
        return RepairService(retrofit)
    }

    @Provides
    @Reusable
    fun provideReviewService(retrofit: Retrofit): ReviewService {
        return ReviewService(retrofit)
    }

    @Provides
    @Reusable
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return ProfileService(retrofit)
    }

    @Provides
    @Reusable
    fun providePreferencesService(retrofit: Retrofit): PreferencesService {
        return PreferencesService(retrofit)
    }
}