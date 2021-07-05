package br.com.cartao_visita_dio.ui.cartao

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author RubioAlves
 * Created 04/07/2021 at 18:33
 */
@Parcelize
data class CartaoArgs(val nome:String, val cargo:String, val empresa:String, val telefone:String, val email:String, val cor: String):Parcelable
