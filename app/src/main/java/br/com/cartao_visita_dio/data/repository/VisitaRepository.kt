package br.com.cartao_visita_dio.data.repository

import br.com.cartao_visita_dio.data.dao.VisitaDao
import br.com.cartao_visita_dio.domain.Visita
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:37
 */
@Singleton
class VisitaRepository @Inject constructor(private val visitaDao: VisitaDao) {

    suspend fun insert(visita: Visita) = visitaDao.insert(visita)
    suspend fun update(visita: Visita) = visitaDao.update(visita)
    suspend fun all():MutableList<Visita> = visitaDao.all()
    suspend fun deleteAll(id:MutableList<Int>) = visitaDao.deleteAll(id)

}
