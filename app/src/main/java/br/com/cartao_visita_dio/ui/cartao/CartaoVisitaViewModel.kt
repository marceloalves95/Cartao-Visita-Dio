package br.com.cartao_visita_dio.ui.cartao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cartao_visita_dio.data.repository.VisitaRepository
import br.com.cartao_visita_dio.domain.Visita
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author RubioAlves
 * Created 04/07/2021 at 14:40
 */
@HiltViewModel
class CartaoVisitaViewModel @Inject constructor(private val repository: VisitaRepository):ViewModel(){

    val listAll = MutableLiveData<MutableList<Visita>>()

    fun adicionarCartao(visita: Visita){
        viewModelScope.launch {
            repository.insert(visita)
        }
    }
    fun atualizarCartao(visita: Visita){
        viewModelScope.launch {
            repository.update(visita)
        }
    }
    fun listarCartoes(){
        viewModelScope.launch {
            listAll.value = repository.all()
        }
    }
    fun deleteAll(id:MutableList<Int>){
        viewModelScope.launch {
            repository.deleteAll(id)
        }
    }

}