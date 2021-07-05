package br.com.cartao_visita_dio.utils.validator

import android.widget.EditText
import br.com.cartao_visita_dio.utils.other.Constants
import com.google.android.material.textfield.TextInputLayout

/**
 * @author RubioAlves
 * Created 04/07/2021 at 15:28
 */
class ValidadorTelefone(private val textInputCampoTelefone: TextInputLayout, private var campo: EditText):Validador{

    private var validadorPadrao: ValidadorPadrao

    init {
        campo = textInputCampoTelefone.editText!!
        validadorPadrao = ValidadorPadrao(textInputCampoTelefone, campo)
    }

    override fun estaValido():Boolean{

        if(!validadorPadrao.estaValido()) {
            return false
        }
        val campoDDD = campo.text.toString()

        adicionarFormatacao(campoDDD)

        return true

    }

    override fun validaCampoObrigatorio(): Boolean {
        val texto = campo.text.toString()

        if (texto.isEmpty()){
            textInputCampoTelefone.error = Constants.CAMPO_OBRIGATORIO
            return false
        }

        return true
    }

    override fun removeError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }


    fun adicionarFormatacao(telefone:String){

        val formatador = FormatadorTelefone()
        val telefoneFormatado = formatador.formata(telefone)
        campo.setText(telefoneFormatado)

    }



}