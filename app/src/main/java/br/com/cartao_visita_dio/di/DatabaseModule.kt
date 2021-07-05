package br.com.cartao_visita_dio.di

import android.content.Context
import androidx.room.Room
import br.com.cartao_visita_dio.data.dao.VisitaDao
import br.com.cartao_visita_dio.data.db.AppDatabase
import br.com.cartao_visita_dio.utils.other.Constants.BD_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author RubioAlves
 * Created 05/07/2021 at 12:50
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, BD_NAME).fallbackToDestructiveMigration().build()


    @Provides
    fun provideVisitaDao(appDatabase: AppDatabase): VisitaDao{
        return appDatabase.visitaDao()
    }

}