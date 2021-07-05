package br.com.cartao_visita_dio.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:47
 */
@HiltAndroidApp
class Application:Application() {

}