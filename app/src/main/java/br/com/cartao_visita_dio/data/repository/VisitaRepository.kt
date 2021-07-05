package br.com.cartao_visita_dio.data.repository

import br.com.cartao_visita_dio.data.dao.VisitaDao
import br.com.cartao_visita_dio.domain.Visita

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:37
 */
class VisitaRepository(private val visitaDao: VisitaDao) {

    suspend fun insert(visita: Visita) = visitaDao.insert(visita)
    suspend fun update(visita: Visita) = visitaDao.update(visita)
    suspend fun all():MutableList<Visita> = visitaDao.all()
    suspend fun deleteAll(id:MutableList<Int>) = visitaDao.deleteAll(id)

}
