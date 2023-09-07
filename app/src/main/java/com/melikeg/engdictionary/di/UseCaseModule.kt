package com.melikeg.engdictionary.di

import com.melikeg.engdictionary.domain.usecase.GetWordUseCase
import com.melikeg.engdictionary.domain.usecase.GetWordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetWordUseCase(bindGetWordUseCaseImpl: GetWordUseCaseImpl): GetWordUseCase
}