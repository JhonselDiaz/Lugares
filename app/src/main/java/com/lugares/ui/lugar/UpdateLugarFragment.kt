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
import androidx.navigation.fragment.navArgs
import com.lugares.R
import com.lugares.databinding.FragmentUpdateLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel

class UpdateLugarFragment : Fragment() {

    //Argumentos
    private val args by navArgs<UpdateLugarFragmentArgs>()
    //
    //Variables
    private lateinit var lugarViewModel: LugarViewModel
    private var _bingind: FragmentUpdateLugarBinding? = null
    private val binding get() = _bingind!!
    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider (this)[LugarViewModel::class.java]
        _bingind = FragmentUpdateLugarBinding.inflate(layoutInflater, container, false)
        //Se coloca la informacion de objeto lugar que se pasa
        binding.etNombre.setText(args.lugar.nombre)
        binding.etTelefono.setText(args.lugar.telefono)
        binding.etCorreo.setText(args.lugar.correo)
        binding.etWeb.setText(args.lugar.web)
        //
        //Se agrega la funcion para actualizar un lugar
        binding.btActualizar.setOnClickListener { updateLugar()
        }
        return binding.root
    }
    //pendiente de corregir
    private fun updateLugar() {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(args.lugar.id, nombre, correo,telefono, web,0.0,0.0,0.0,"","" )
            lugarViewModel.updateLugar(lugar)
            Toast.makeText(requireContext(),getString(R.string.LugarModificado), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLugarFragment2_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(),getString(R.string.noData), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView(){
        super.onDestroyView()
        _bingind = null
    }
}