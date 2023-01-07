package com.example.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.example.tracker_data.local.dao.TrackerDao
import com.example.tracker_data.local.dao.TrackerDatabase
import com.example.tracker_data.remote.OpenFoodAPI
import com.example.tracker_data.repository.TrackedRepositoryImpl
import com.example.tracker_domain.repository.TrackerRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodAPI(client: OkHttpClient): OpenFoodAPI {
        return Retrofit.Builder()
            .baseUrl(OpenFoodAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackedRepository(
        api: OpenFoodAPI,
        database: TrackerDatabase
    ): TrackerRepository {
        return TrackedRepositoryImpl(
            dao = database.dao,
            api = api
        )
    }
}