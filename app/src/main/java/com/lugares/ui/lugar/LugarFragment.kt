package com.lugares.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lugares.R
import com.lugares.adapter.LugarAdapter
import com.lugares.databinding.FragmentAddLugarBinding
import com.lugares.databinding.FragmentLugarBinding
import com.lugares.viewmodel.LugarViewModel

class LugarFragment(private var _bingind: FragmentLugarBinding? = null) : Fragment() {

    private lateinit var lugarViewModel: LugarViewModel
    private val binding get() = _bingind!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider (this)[LugarViewModel::class.java]
        _bingind = FragmentLugarBinding.inflate(layoutInflater, container, false)

        //Se programa la accion para pasarse a AddLugar
        binding.addLugarButton.setOnClickListener{
            findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
        }
        //Activar el Reciclador -RecyclerView
        val lugarAdapter = LugarAdapter()
        val reciclador = binding.reciclador

        reciclador.adapter = lugarAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        lugarViewModel = ViewModelProvider(this)[LugarViewModel::class.java]

        lugarViewModel.getAllData.observe(viewLifecycleOwner){
            lugares -> lugarAdapter.setData(lugares)
        }


        return binding.root
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _bingind = null
    }
}