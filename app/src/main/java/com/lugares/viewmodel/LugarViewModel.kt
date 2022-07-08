package com.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares.data.LugarDao
import com.lugares.model.Lugar
import com.lugares.repository.LugarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {

        val getAllData: MutableLiveData<List<Lugar>>

        //Esta es la menera de como accedo al repositorio desde el view model
        private val repository : LugarRepository = LugarRepository(LugarDao())

        //Se inicializan los atributos anteriores de esta clase
        init {getAllData = repository.getAllData}
       //Esta funcion de alto nivel llama al subproceso de I/O para insertar/actualizar un regristro Lugar
    fun saveLugar(lugar:Lugar){
        viewModelScope.launch(Dispatchers.IO){
            repository.saveLugar(lugar)
        }
    }
    //Esta funcion de alto nivel llama al subproceso de I/O para eliminar un regristro Lugar
    fun deleteLugar(lugar:Lugar){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteLugar(lugar)
        }
    }




}