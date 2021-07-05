package br.com.cartao_visita_dio.utils.validator

import android.util.Patterns
import android.widget.EditText
import br.com.cartao_visita_dio.utils.other.Constants
import com.google.android.material.textfield.TextInputLayout

/**
 * @author RubioAlves
 * Created 05/07/2021 at 08:21
 */
class ValidadorEmail(private val textInputCampoEmail: TextInputLayout, private var campo: EditText):Validador {

    private var validadorPadrao: ValidadorPadrao

    init {
        campo = textInputCampoEmail.editText!!
        validadorPadrao = ValidadorPadrao(textInputCampoEmail, campo)
    }

    override fun estaValido():Boolean{

        if(!validadorPadrao.estaValido()) {
            return false
        }

        return true

    }

    override fun validaCampoObrigatorio(): Boolean {
        val texto = campo.text.toString()

        if (texto.isEmpty()){
            textInputCampoEmail.error = Constants.CAMPO_OBRIGATORIO
            return false
        }

        return true
    }

    override fun removeError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    fun validarEmail(): Boolean {

        val validarEmail = campo.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(validarEmail).matches()){
            textInputCampoEmail.error = Constants.CAMPO_OBRIGATORIO_EMAIL
            return false
        }

        return true

    }


}