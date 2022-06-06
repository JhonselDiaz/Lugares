package com.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares.data.LugarDatabase
import com.lugares.model.Lugar
import com.lugares.repository.LugarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {

        val getAllData: LiveData<List<Lugar>>

        //Esta es la menera de como accedo al repositorio desde el view model
        private val repository : LugarRepository

        //Se inicializan los atributos anteriores de esta clase
        init {
            val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
            repository = LugarRepository(lugarDao)
            getAllData = repository.getAllData
        }
       //Esta funcion de alto nivel llama al subproceso de I/O para grabar un regristro Lugar
    fun addLugar(lugar:Lugar){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLugar(lugar)
        }
    }
    //Esta funcion de alto nivel llama al subproceso de I/O para actualizar un regristro Lugar
    fun updateLugar(lugar:Lugar){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLugar(lugar)
        }
    }
    //Esta funcion de alto nivel llama al subproceso de I/O para eliminar un regristro Lugar
    fun deleteLugar(lugar:Lugar){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteLugar(lugar)
        }
    }




}