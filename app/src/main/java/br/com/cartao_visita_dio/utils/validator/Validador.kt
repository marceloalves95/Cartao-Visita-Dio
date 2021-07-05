package br.com.cartao_visita_dio.utils.validator

import com.google.android.material.textfield.TextInputLayout

/**
 * @author RubioAlves
 * Created 04/07/2021 at 15:26
 */
interface Validador {
    fun estaValido():Boolean
    fun validaCampoObrigatorio():Boolean
    fun removeError(textInputLayout: TextInputLayout)
}