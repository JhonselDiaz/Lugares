package com.lugares.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lugares.R
import com.lugares.databinding.FragmentAddLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel

class AddLugarFragment : Fragment() {

    private lateinit var lugarViewModel: LugarViewModel

    private var _bingind: FragmentAddLugarBinding? = null

    private val binding get() = _bingind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider (this)[LugarViewModel::class.java]
        _bingind = FragmentAddLugarBinding.inflate(layoutInflater, container, false)

        //Se agrega la funcion para agregar un lugar
        binding.btAgregar.setOnClickListener {
            addLugar()
        }
        return binding.root
    }

    private fun addLugar() {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(0, nombre, correo,telefono, web,0.0,0.0,0.0,"","" )
            lugarViewModel.addLugar(lugar)
            Toast.makeText(requireContext(),getString(R.string.lugarAdded), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(),getString(R.string.noData), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView(){
        super.onDestroyView()
        _bingind = null
    }
}