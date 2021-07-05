package br.com.cartao_visita_dio.utils.other

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.cartao_visita_dio.R

/**
 * @author RubioAlves
 * Created 27/06/2021 at 19:08
 */

class CorAdapter(context: Context, private val nomeCores:List<String>, private val cores:List<String>, resource:Int):ArrayAdapter<String>(context, resource, nomeCores){

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View = convertView ?: layoutInflater.inflate(R.layout.lista_cores, parent, false)
        getItem(position)?.let { cor ->
            setCor(view, cores[position], cor)
        }
        return view
    }

    override fun getCount(): Int = nomeCores.size

    private fun setCor(view: View, cor:String, nomeCorTarefa:String) {

        val nomeCor = view.findViewById<TextView>(R.id.nomeCor)
        val corTarefa = view.findViewById<TextView>(R.id.corTarefa)

        nomeCor.text = nomeCorTarefa
        corTarefa.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            cornerRadius = 48f
            setColor(Color.parseColor(cor))
        }
    }

}