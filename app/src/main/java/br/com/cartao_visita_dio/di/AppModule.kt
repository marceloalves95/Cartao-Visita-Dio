package br.com.cartao_visita_dio.di

import androidx.room.Room
import br.com.cartao_visita_dio.data.db.AppDatabase
import br.com.cartao_visita_dio.data.repository.VisitaRepository
import br.com.cartao_visita_dio.ui.cartao.CartaoVisitaViewModel
import br.com.cartao_visita_dio.utils.other.Constants.BD_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:51
 */
val appModules = module {
    single {
        Room.databaseBuilder(
            get(),
            //Aqui você define a classe do seu banco de dados
            AppDatabase::class.java,
            //Nome do seu banco
            BD_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    single{
        //Módulo da interface do banco
        get<AppDatabase>().visitaDao()
    }
    single {
        //Repository
        VisitaRepository(get())
    }
    viewModel {
        //View Model
        CartaoVisitaViewModel(get())
    }

}
