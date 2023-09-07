package com.melikeg.engdictionary.di

import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.presentation.home.HomeUiData
import com.melikeg.engdictionary.presentation.home.HomeUiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {

    @Binds
    @ViewModelScoped
    abstract fun bindHomeUiMapper(homeUiMapperImpl: HomeUiMapperImpl): WordMapper<WordItem, HomeUiData>
}