package com.example.carmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.carmanagement.model.database.CarRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(
        app: Application,
        callback: CarRoomDatabase.Callback
    ) = Room.databaseBuilder(app, CarRoomDatabase::class.java, "car_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideCarDao(db: CarRoomDatabase) = db.carDao()



    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope