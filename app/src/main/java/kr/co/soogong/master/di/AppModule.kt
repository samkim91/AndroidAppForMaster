package kr.co.soogong.master.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.contract.AppDatabaseContract
import kr.co.soogong.master.contract.AppSharedPreferenceContract
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.source.local.AppDatabase
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
//        tokenInterceptor: TokenInterceptor,
//        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "http")
        val cacheSize = 32 * 1024 * 1024L
        val client = OkHttpClient.Builder()
            .cache(Cache(httpCacheDirectory, cacheSize))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                } else {
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
        // Firebase Auth??? ??????????????? ?????? ????????? ?????? ????????? ??? ??????.. ?????? ??????..
//            .addInterceptor(tokenInterceptor)
//            .authenticator(tokenAuthenticator)

        val okHttpClient = client.build()

        okHttpClient.dispatcher.maxRequests = 16

        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
//            .registerTypeAdapter(ArrayList::class.java, NonNullListDeserializer<Any>())
//            .registerTypeAdapter(String::class.java, StringTypeAdapter())
//            .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
//            .registerTypeAdapter(Int::class.java, IntTypeAdapter())
//            .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(
                when (BuildConfig.BUILD_TYPE) {
                    "release" -> HttpContract.PROD_URL
                    "internal" -> HttpContract.TEST_URL
                    "local" -> HttpContract.LOCAL_URL_MH
                    else -> HttpContract.DEV_URL
                }
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabaseContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideAppSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            AppSharedPreferenceContract.PREFERENCES_NAME,
            MODE_PRIVATE
        )
        // TODO : ????????? ????????? ????????? ?????? ????????? ????????? ?????????
        /*
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(keyGenParameterSpec)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            AppSharedPreferenceContract.PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
         */
    }
}