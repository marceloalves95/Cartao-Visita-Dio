package br.com.cartao_visita_dio.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.cartao_visita_dio.R
import br.com.cartao_visita_dio.databinding.DetailFragmentBinding
import br.com.cartao_visita_dio.domain.Visita
import br.com.cartao_visita_dio.ui.cartao.CartaoVisitaViewModel
import br.com.cartao_visita_dio.ui.main.MainActivity
import br.com.cartao_visita_dio.utils.other.Cor
import br.com.cartao_visita_dio.utils.other.CorAdapter
import br.com.cartao_visita_dio.utils.validator.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author RubioAlves
 * Created 02/07/2021 at 17:27
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var nome: String
    private lateinit var cargo: String
    private lateinit var empresa: String
    private lateinit var telefone: String
    private lateinit var email: String
    private lateinit var cor: String

    private val nomeCores: MutableList<String> = mutableListOf()
    private val cores: MutableList<String> = mutableListOf()
    private val validadores: MutableList<Validador> = ArrayList()
    private val viewModel: CartaoVisitaViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        initCampos()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initValidadores()
    }



    private fun salvarCartao() {

        val estaValido = validarTodosCampos()


        if (estaValido) {

            iniciarCampos()
            val visita = Visita(0, nome, cargo, empresa, telefone, email, cor, false)
            viewModel.adicionarCartao(visita)
            Toast.makeText(requireContext(), "Cartão cadastrado com sucesso", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_detail_cartao_visita_to_navigation_cartao_visita)

        }


    }

    private fun alterarCartao() {


        val estaValido = validarTodosCampos()

        if (estaValido) {

            iniciarCampos()
            val visita = Visita(args.id, nome, cargo, empresa, telefone, email, cor, false)
            viewModel.atualizarCartao(visita)
            Toast.makeText(requireContext(), "Cartão alterado com sucesso", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_detail_cartao_visita_to_navigation_cartao_visita)

        }


    }

    private fun validarTodosCampos(): Boolean {

        var estaValido = true
        validadores.forEach { validador -> if (!validador.estaValido()) estaValido = false }
        return estaValido

    }

    private fun iniciarCampos() {

        binding.let { visita ->
            nome = visita.nome.text.toString().trim()
            cargo = visita.cargo.text.toString().trim()
            empresa = visita.empresa.text.toString().trim()
            telefone = visita.telefone.text.toString().trim()
            email = visita.email.text.toString().trim()
            cor = visita.cores.text.toString().trim()
        }

    }

    private fun initList() {

        binding.run {
            with(cores) {
                val adapter = CorAdapter(
                    requireContext(),
                    listarNomeCores(),
                    listarCores(),
                    android.R.layout.simple_list_item_1
                )
                setAdapter(adapter)
            }
        }

    }

    private fun initCampos() {

        if (args.cartao == null) {

            (activity as MainActivity).supportActionBar?.setTitle(R.string.cadastrar_cartao_visita)


            with(binding.visita) {
                iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
                text = getString(R.string.cadastrar_cartao)
                setIconResource(R.drawable.ic_card_plus)
                setOnClickListener {

                    if (binding.telefone.isFocusable) binding.campoTelefone.requestFocus()
                    salvarCartao()
                }

            }
        } else {

            (activity as MainActivity).supportActionBar?.setTitle(R.string.alterar_cartao_visita)

            initList()

            args.cartao.let { args ->

                binding.run {

                    nome.setText(args?.nome)
                    cargo.setText(args?.cargo)
                    empresa.setText(args?.empresa)
                    telefone.setText(args?.telefone)
                    email.setText(args?.email)
                    cores.setText(args?.cor)

                    with(visita) {
                        iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
                        text = getString(R.string.alterar_cartao)
                        setIconResource(R.drawable.ic_update)
                        setOnClickListener {
                            if (binding.telefone.isFocusable) binding.campoTelefone.requestFocus()
                            alterarCartao()
                        }
                    }
                }


            }
        }

    }

    private val listarNomeCores: () -> MutableList<String> = {

        Cor.values().forEach { nomesCores -> nomeCores.add(nomesCores.nomeCor) }

        nomeCores

    }

    private fun listarCores(): MutableList<String> {

        Cor.values().forEach { cor -> cores.add(cor.cor) }

        return cores

    }

    private fun initValidadores() {
        with(binding) {
            validadorPadrao(campoNome)
            validadorPadrao(campoCargo)
            validadorPadrao(campoEmpresa)
            validadorTelefone(campoTelefone)
            validadorEmail(campoEmail)
            validadorMaterialComplete(campoCores)
        }
    }

    private fun validadorPadrao(textInputLayout: TextInputLayout) {

        val campo = textInputLayout.editText

        val validado = ValidadorPadrao(textInputLayout, campo as TextInputEditText)
        validadores.add(validado)

        campo.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {
                if (!validado.estaValido()) {
                    return@setOnFocusChangeListener
                } else {
                    validado.removeError(textInputLayout)
                }

            }

        }
    }

    private fun validadorTelefone(textInputLayout: TextInputLayout) {

        val campo = textInputLayout.editText

        val validadorTelefone = ValidadorTelefone(textInputLayout, campo as TextInputEditText)

        val formatador = FormatadorTelefone()
        validadores.add(validadorTelefone)
        campo.setOnFocusChangeListener { _, hasFocus ->

            val telefoneDDD = campo.text.toString()

            if (hasFocus) {
                val telefoneRemovido = formatador.remove(telefoneDDD)
                campo.setText(telefoneRemovido)
            } else {
                validadorTelefone.estaValido()
            }

        }
    }

    private fun validadorEmail(textInputLayout: TextInputLayout) {

        val campo = textInputLayout.editText

        val validadorEmail = ValidadorEmail(textInputLayout, campo as TextInputEditText)
        validadores.add(validadorEmail)
        campo.setOnFocusChangeListener { _, hasFocus ->

            val email = campo.text.toString()

            if (hasFocus) {
                campo.setText(email)
            } else {
                validadorEmail.estaValido()
                validadorEmail.validarEmail()
            }

        }
    }

    private fun validadorMaterialComplete(textInputLayout: TextInputLayout) {

        val campo = textInputLayout.editText

        val validado = ValidadorPadrao(textInputLayout, campo as MaterialAutoCompleteTextView)
        validadores.add(validado)
        campo.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {

                if (!validado.estaValido()) {
                    return@setOnFocusChangeListener
                } else {
                    validado.removeError(textInputLayout)
                }

            }

        }
    }


}