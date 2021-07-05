package br.com.cartao_visita_dio.data.dao

import androidx.room.*
import br.com.cartao_visita_dio.domain.Visita

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:32
 */
@Dao
interface VisitaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visita: Visita)
    @Update
    suspend fun update(visita: Visita)
    @Query("SELECT * FROM visita")
    suspend fun all():MutableList<Visita>
    @Query("DELETE FROM visita WHERE id IN (:id)")
    suspend fun deleteAll(id:MutableList<Int>)
}
