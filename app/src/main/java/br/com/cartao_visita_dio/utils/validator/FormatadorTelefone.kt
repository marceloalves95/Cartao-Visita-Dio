package br.com.cartao_visita_dio.utils.validator

/**
 * @author RubioAlves
 * Created 04/07/2021 at 15:32
 */
class FormatadorTelefone {

    private val regex = "([0-9]{2})([0-9]{4,5})([0-9]{4})".toRegex()
    private val replacement = "($1) $2-$3"

    fun formata(telefone:String):String = telefone.replace(regex,replacement)
    fun remove(telefone: String):String {

        return telefone
            .replace("(", "")
            .replace(")", "")
            .replace(" ", "")
            .replace("-", "")
    }


}