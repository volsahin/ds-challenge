package com.tinyfalcon.dschallenge.feature.home

import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @ViewModelScoped
    @Provides
    fun provideMusicApi(retrofit: Retrofit): MusicService {
        return retrofit.create(MusicService::class.java)
    }
}