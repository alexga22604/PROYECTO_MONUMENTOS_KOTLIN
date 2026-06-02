package com.example.monumentos_practica6.di

import android.content.Context
import com.example.monumentos_practica6.data.local.MyMonumentPreferences
import com.example.monumentos_practica6.data.repository.MonumentRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideMyMonumentPreferences(@ApplicationContext context: Context): MyMonumentPreferences {
        return MyMonumentPreferences(context)
    }

    @Provides
    @Singleton
    fun provideMonumentRepository(gson: Gson): MonumentRepository {
        return MonumentRepository(gson)
    }
}