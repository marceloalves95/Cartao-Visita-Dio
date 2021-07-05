package br.com.cartao_visita_dio.ui.cartao

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import androidx.core.util.isNotEmpty
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.cartao_visita_dio.databinding.CartaoVisitaAdapterBinding
import br.com.cartao_visita_dio.domain.Visita
import br.com.cartao_visita_dio.utils.other.Cor
import java.util.*

/**
 * @author RubioAlves
 * Created 02/07/2021 at 17:16
 */
class CartaoVisitaAdapter(private val fragment:CartaoVisitaFragment, val lista: MutableList<Visita>) : RecyclerView.Adapter<CartaoVisitaAdapter.CartaoVisitaAdapterViewHolder>(), Filterable {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos = -1
    private var listaAll: MutableList<Visita> = ArrayList()

    init {
        listaAll.addAll(lista)
    }

    inner class CartaoVisitaAdapterViewHolder(private val itemBinding: CartaoVisitaAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(visita: Visita) {

            val locale = Locale("pt", "BR")
            val letraMaiuscula = visita.nome.substring(0,1).uppercase(locale)

            itemBinding.run {

                textViewEmpresa.text = visita.empresa
                textViewCargo.text = visita.cargo
                textViewTelefone.text = visita.telefone
                textViewEmail.text = visita.email

                with(fabShare){
                    setOnClickListener {
                        visibility = View.INVISIBLE
                        listenerShare(cardView)
                        visibility = View.VISIBLE
                    }
                }
                with(cardView){

                    apply {
                        isChecked = visita.selected
                    }
                    setOnClickListener {
                        if (selectedItems.isNotEmpty()) onItemClick?.invoke(adapterPosition)
                        fragment.actionMode?.finish()
                        listaArgumentos(visita)
                    }
                    setOnLongClickListener {
                        onItemLongClick?.invoke(adapterPosition)
                        return@setOnLongClickListener true
                    }
                }
                listarCores().forEach { cores->

                    if (visita.cor == cores.nomeCor){

                        textViewNome.apply {
                            text = visita.nome
                            setTextColor(Color.parseColor(cores.textColor))
                        }
                        corCartao.apply {
                            setBackgroundColor(Color.parseColor(cores.cor))
                        }
                        letra.apply {

                            text = letraMaiuscula
                            setTextColor(Color.parseColor(cores.textColor))
                            background = GradientDrawable().apply {

                                shape = GradientDrawable.OVAL
                                cornerRadius = 48f
                                setColor(Color.parseColor(cores.cor))

                            }
                        }
                        cardView.apply {

                            strokeColor = Color.parseColor(cores.cor)

                            if (isChecked){
                                strokeColor = Color.parseColor(cores.corPrimaryDark)
                            }
                        }

                    }

                }

                if (currentSelectedPos == adapterPosition) currentSelectedPos = -1

            }

        }

    }

    override fun onCreateViewHolder(
        parent: android.view.ViewGroup,
        viewType: Int
    ): CartaoVisitaAdapterViewHolder {

        val itemBinding =
            CartaoVisitaAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartaoVisitaAdapterViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: CartaoVisitaAdapterViewHolder, position: Int) {

        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

    private fun listarCores(): MutableList<Cor> {

        val cores: MutableList<Cor> = mutableListOf()

        Cor.values().forEach { cor ->

            cores.add(cor)

        }

        return cores

    }

    private val onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null
    var listenerShare: (View) -> Unit = {}

    fun toggleSelection(position: Int) {

        currentSelectedPos = position
        if (selectedItems[position, false]) {
            selectedItems.delete(position)

            lista[position].selected = false

        } else {
            selectedItems.put(position, true)
            lista[position].selected = true
        }

        notifyItemChanged(position)


    }

    fun deletarCartao() {

        lista.removeAll(lista.filter { it.selected })

        notifyDataSetChanged()
        currentSelectedPos = -1


    }

    private val listaArgumentos: (Visita) -> Unit = { visita ->

        with(visita) {
            val cartao = CartaoArgs(nome, cargo, empresa, telefone, email, cor)
            val id = visita.id
            val action = CartaoVisitaFragmentDirections.actionNavigationCartaoVisitaToNavigationDetailCartaoVisita(id, cartao)
            fragment.findNavController().navigate(action)

        }

    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filteredList: MutableList<Visita> = ArrayList()

                val locale = Locale("pt", "BR")

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(listaAll)
                } else {

                    listaAll.forEach { visita ->

                        with(visita) {
                            if (nome.lowercase(locale).contains(constraint.toString().lowercase(locale),true)
                                || cargo.lowercase(locale)
                                    .contains(constraint.toString().lowercase(locale),true)
                                || empresa.lowercase(locale)
                                    .contains(constraint.toString().lowercase(locale),true)
                                || email.lowercase(locale)
                                    .contains(constraint.toString().lowercase(locale),true)
                                || telefone.lowercase(locale)
                                    .contains(constraint.toString().lowercase(locale),true)
                            ) {
                                filteredList.add(visita)
                            }
                        }

                    }

                }
                val results = FilterResults()
                results.values = filteredList

                return results


            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                lista.clear()
                lista.addAll(results?.values as MutableList<Visita>)
                notifyDataSetChanged()

            }
        }

    }

}