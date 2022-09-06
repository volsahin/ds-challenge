package com.tinyfalcon.dschallenge.feature.home

import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.GetSessionsMapper
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SearchSessionsMapper
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SessionMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @ViewModelScoped
    @Provides
    fun provideMusicApi(retrofit: Retrofit): MusicService {
        return retrofit.create(MusicService::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Qualifier
    annotation class SearchMapper

    @Qualifier
    annotation class GetSessionMapper

    @GetSessionMapper
    @ViewModelScoped
    @Binds
    abstract fun bindGetSessionMapper(impl: GetSessionsMapper): SessionMapper

    @SearchMapper
    @ViewModelScoped
    @Binds
    abstract fun bindSearchMapper(impl: SearchSessionsMapper): SessionMapper
}
