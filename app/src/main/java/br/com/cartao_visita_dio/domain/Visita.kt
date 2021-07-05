package br.com.cartao_visita_dio.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author RubioAlves
 * Created 02/07/2021 at 17:19
 */
@Entity(tableName = "visita")
data class Visita(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val nome:String,
    val cargo:String,
    val empresa:String,
    val telefone:String,
    val email:String,
    val cor: String,
    var selected: Boolean
)