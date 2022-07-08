package com.lugares.repository


import androidx.lifecycle.MutableLiveData
import com.lugares.data.LugarDao
import com.lugares.model.Lugar

class LugarRepository (private val lugarDao: LugarDao) {    //Se implementa las funciones de la interface

    //Se crea un objeto que el arrayList de los registros de la tabla
    val getAllData: MutableLiveData<List<Lugar>> = lugarDao.getAllData()


    //se define la funcion para insertar o actualizar un Lugar en la coleccion lugar
     fun saveLugar(lugar: Lugar) {
        lugarDao.saveLugar(lugar)
    }

    //se define la funcion para eliminar un Lugar en la tabla lugar
     fun deleteLugar(lugar: Lugar) {
        lugarDao.deleteLugar(lugar)
    }

}