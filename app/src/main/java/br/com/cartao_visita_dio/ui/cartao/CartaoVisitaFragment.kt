package br.com.cartao_visita_dio.ui.cartao

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cartao_visita_dio.R
import br.com.cartao_visita_dio.databinding.CartaoVisitaFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.Manifest
import br.com.cartao_visita_dio.utils.other.Image

/**
 * @author RubioAlves
 * Created 02/07/2021 at 16:17
 */
class CartaoVisitaFragment : Fragment() {

    private var _binding: CartaoVisitaFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel:CartaoVisitaViewModel by viewModel()
    private lateinit var cartaoAdapter:CartaoVisitaAdapter
    private val listarId = mutableListOf<Int>()
    var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CartaoVisitaFragmentBinding.inflate(inflater, container, false)
        initViews()
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cartaoAdapter.filter.filter(newText)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews(){

        binding.run {

            viewModel.listarCartoes()
            viewModel.listAll.observe(viewLifecycleOwner,{ listarCartoes->

                cartaoAdapter = CartaoVisitaAdapter(this@CartaoVisitaFragment, listarCartoes)

                cartaoAdapter.apply {
                    onItemLongClick={
                        enableActionMode(it)
                    }
                    listenerShare = { card ->
                        Image.share(requireContext(), card)
                    }
                }
                with(recyclerView){
                    adapter = cartaoAdapter
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                }

            })

            with(fab){
                setOnClickListener {
                    findNavController().navigate(R.id.action_navigation_cartao_visita_to_navigation_detail_cartao_visita)
                }
            }

        }


    }

    private fun enableActionMode(position: Int) {

        if (actionMode == null) actionMode = activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_cartao, menu)
                mode?.title = resources.getString(R.string.deletar_cartao)

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.action_delete){
                    deletarCartao()
                    mode?.finish()
                    return true

                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

                with(cartaoAdapter){

                    selectedItems.clear()
                    lista.filter { it.selected }.forEach { it.selected = false }
                    notifyDataSetChanged()

                }

                actionMode = null
            }

        })

        cartaoAdapter.toggleSelection(position)
        val size = cartaoAdapter.selectedItems.size()
        if (size == 0){
            actionMode?.finish()
        }else{
            actionMode?.subtitle = "$size cartão(õs) selecionado(s)"
            actionMode?.invalidate()

        }

    }

    private fun deletarCartao() {

        val list = cartaoAdapter.lista.filter { it.selected }

        list.forEach { tarefa -> listarId.add(tarefa.id) }

        viewModel.deleteAll(listarId)
        cartaoAdapter.deletarCartao()
        Toast.makeText(requireContext(), "Cartão(õs) deletada(s) com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun setUpPermissions() {
        // write permission to access the storage
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }

}
