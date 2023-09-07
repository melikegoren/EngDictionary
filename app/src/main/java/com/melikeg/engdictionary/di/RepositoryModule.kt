package com.melikeg.engdictionary.di

import com.melikeg.engdictionary.data.repository.DictionaryRepositoryImpl
import com.melikeg.engdictionary.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDictionaryRepository(bindDictionaryRepositoryImpl: DictionaryRepositoryImpl): DictionaryRepository
}