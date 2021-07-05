package br.com.cartao_visita_dio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.cartao_visita_dio.data.dao.VisitaDao
import br.com.cartao_visita_dio.domain.Visita

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:35
 */
@Database(entities = [Visita::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun visitaDao():VisitaDao
}