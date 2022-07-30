package com.lugares.ui.lugar


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lugares.R
import com.lugares.databinding.FragmentUpdateLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import com.bumptech.glide.Glide

class UpdateLugarFragment : Fragment() {

    //Argumentos
    private val args by navArgs<UpdateLugarFragmentArgs>()
    //
    //Variables
    private lateinit var lugarViewModel: LugarViewModel
    private var _bingind: FragmentUpdateLugarBinding? = null
    private val binding get() = _bingind!!
    //
    //Esuchar audio grabado
    private lateinit var mediaPlayer: MediaPlayer

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
        binding.btActualizar.setOnClickListener { updateLugar()}
        //
        binding.tvAltura.text = args.lugar.altura.toString()
        binding.tvLatitud.text = args.lugar.latitud.toString()
        binding.tvLongitud.text = args.lugar.latitud.toString()

        binding.btEmail.setOnClickListener{escribirCorreo()}
        binding.btPhone.setOnClickListener{llamarLugar()}
        binding.btWeb.setOnClickListener{verWeb()}
        //Para iniciar y activar el boton de plau si hay un audio
        if(args.lugar.rutaAudio?.isNotEmpty()==true){
            mediaPlayer=MediaPlayer()
            mediaPlayer.setDataSource(args.lugar.rutaAudio)
            mediaPlayer.prepare()
            binding.btPlay.isEnabled=true
            binding.btPlay.setOnClickListener{mediaPlayer.start()}
        }else{
            binding.btPlay.isEnabled=false
        }
        //Si hay una ruta de imagen la dibuja
        if(args.lugar.rutaImagen?.isNotEmpty()==true){
            Glide.with(requireContext())
                .load(args.lugar.rutaImagen)
                .fitCenter()
                .into(binding.imagen)
        }
        binding.btWhatsapp.setOnClickListener{enviarWhatsApp()}
        binding.btLocation.setOnClickListener{verMapa()}
        //se agrega una opcion de menu en esta pantalla
        setHasOptionsMenu(true)
       return binding.root
    }
    private fun enviarWhatsApp() {
        val telefono = binding.etTelefono.text.toString()
        if(telefono.isNotEmpty()){
            val sendIntent = Intent(Intent.ACTION_VIEW)
            val uri= "whatsapp://send?phone=506$telefono&"+getString(R.string.msg_saludos)
            sendIntent.setPackage("com.whatsapp")
            sendIntent.data=Uri.parse(uri)
            startActivity(sendIntent)
            }else{
            Toast.makeText(requireContext(),getString(R.string.msg_datos),Toast.LENGTH_SHORT).show()
            }
    }
    private fun verMapa() {
        val latitud = binding.tvLatitud.text.toString().toDouble()
        val longitud = binding.tvLongitud.text.toString().toDouble()
        if(latitud.isFinite() && longitud.isFinite()){
            val location = Uri.parse("geo:$latitud,$longitud?z18")
            val mapIntent = Intent(Intent.ACTION_VIEW,location)
            startActivity(mapIntent)
        }else{
        }
    }
    private fun verWeb() {
        val recurso = binding.etWeb.text.toString()
        if(recurso.isNotEmpty()){
            //se abre el sitio web

            val rutina = Intent(Intent.ACTION_VIEW, Uri.parse("http://$recurso"))
            startActivity(rutina)//Levanta el browser
        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun llamarLugar() {
        val recurso = binding.etTelefono.text.toString()
        if(recurso.isNotEmpty()){
            //se activa el correo
            val rutina = Intent(Intent.ACTION_CALL)
            rutina.data = Uri.parse("tel:$recurso")
            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED){
                //Se solicitan los permisos, porque no estan otorgados
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),105)
            }else{
                requireActivity().startActivity(rutina) //Hacer llamada
            }
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_datos),Toast.LENGTH_SHORT).show()
        }
    }
    private fun escribirCorreo(){
        //
        val recurso = binding.etCorreo.text.toString()
        if(recurso.isNotEmpty()){
            //se activa el correo
            val rutina = Intent(Intent.ACTION_SEND)
            rutina.type= "message/rfc822" // "rfc822" tipo de codigo para activar el correo
            rutina.putExtra(Intent.EXTRA_EMAIL, arrayOf(recurso))
            rutina.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.msg_saludos)+" "+binding.etNombre.text)
            rutina.putExtra(Intent.EXTRA_TEXT,getString(R.string.msg_mensaje_correo))
            startActivity(rutina)//Levanta el correo y lo presenta para modificar y enviar
        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }
    //Aca se genera el menu con el icono de borrar/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //"R" una clase de recursos
        inflater.inflate(R.menu.delete_menu, menu)
    }
    //Aca se programa que si se detecta un clock en el icono borrar haga algo
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Se verifica se si dio click en el icono de borrar.
        if(item.itemId == R.id.menu_delete){
            deleteLugar()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteLugar() {
        val consulta = AlertDialog.Builder(requireContext())

        consulta.setTitle(R.string.delete)
        consulta.setMessage(getString(R.string.seguroBorrar)+" ${args.lugar.nombre}?")
        //Acciones a la respuesta "Si"
        consulta.setPositiveButton(getString(R.string.si)) {_,_ ->
            lugarViewModel.deleteLugar(args.lugar)
            findNavController().navigate(R.id.action_updateLugarFragment2_to_nav_lugar)
        }
        consulta.setNegativeButton(getString(R.string.no)){_,_ ->}
        consulta.create().show()
    }
    //pendiente de corregir
    private fun updateLugar() {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(args.lugar.id, nombre, correo,telefono, web,0.0,0.0,0.0,"","" )
            lugarViewModel.saveLugar(lugar)
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