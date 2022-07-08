package com.lugares.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.lugares.model.Lugar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase


class  LugarDao {

    private val coleccion1 = "lugaresApp"
    private val usuario = Firebase.auth.currentUser?.email.toString();
    private val coleccion2 = "misLugares"

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance();

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun saveLugar(lugar: Lugar) {
        val documento: DocumentReference
        if (lugar.id.isEmpty()) {
            documento =firestore.collection(coleccion1).document(usuario).collection(coleccion2).document()
            lugar.id = documento.id
         }else{
            documento =firestore.collection(coleccion1).document(usuario).collection(coleccion2).document()
        }
        documento.set(lugar)
            .addOnSuccessListener {
                Log.d("saveLugar", "Lugar Agregado/Modificado")
            }
            .addOnCanceledListener {
                Log.e("saveLugar", "Error Lugar NO agregado/Modificado")
            }
    }
    fun deleteLugar(lugar: Lugar){
        if(lugar.id.isNotEmpty()){
            firestore.collection(coleccion1).document(usuario).collection(coleccion2).document(lugar.id).delete()
                .addOnSuccessListener {
                    Log.d("deleteLugar", "Lugar eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteLugar", "Error Lugar NO eliminado")
                }
        }
    }
    fun getAllData(): MutableLiveData<List<Lugar>> {
        val listaLugares = MutableLiveData<List<Lugar>>()
        //Recuperar todos los documentos/lugares de nuestra coleccion "misLugares"
        firestore.collection(coleccion1).document(usuario).collection(coleccion2)
            .addSnapshotListener { instantenea, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (instantenea != null) {//Si hay informacion recupera los datos
                    val lista = ArrayList<Lugar>()
                    //se recorre la instantea..
                    instantenea.documents.forEach {
                        val lugar = it.toObject(Lugar::class.java)
                        if (lugar != null) {
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }
            }
        return listaLugares
    }
}
