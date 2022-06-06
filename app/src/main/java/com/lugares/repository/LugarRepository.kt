package com.lugares.repository

import androidx.lifecycle.LiveData
import com.lugares.data.LugarDao
import com.lugares.model.Lugar

class LugarRepository (private val lugarDao: LugarDao) {
    //Se implementa las funciones de la interface

    //Se crea un objeto que el arrayList de los registros de la tabla
    val getAllData: LiveData<List<Lugar>> = lugarDao.getAllData()


    //se define la funcion para insertar un Lugar en la tabla lugar
    suspend fun addLugar(lugar:Lugar) {
        lugarDao.addLugar(lugar)
    }
        //se define la funcion para actualizar un Lugar en la tabla lugar
        suspend fun updateLugar(lugar:Lugar) {
            lugarDao.updateLugar(lugar)
        }
            //se define la funcion para eliminar un Lugar en la tabla lugar
            suspend fun deleteLugar(lugar:Lugar){
                lugarDao.deleteLugar(lugar)

            }
}