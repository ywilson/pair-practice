package com.example.practice.DI

import android.content.Context
import com.example.practice.repository.CharacterRepository
import com.example.practice.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PersistenceModule {

    @Binds
    abstract fun provideCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl) : CharacterRepository

    @Binds
    abstract fun provideContext(@ApplicationContext context: Context) : Context
}