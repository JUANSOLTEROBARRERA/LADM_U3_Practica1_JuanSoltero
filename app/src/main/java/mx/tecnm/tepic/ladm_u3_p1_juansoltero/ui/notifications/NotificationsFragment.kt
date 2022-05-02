package mx.tecnm.tepic.ladm_u3_p1_juansoltero.ui.notifications

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.Arrendamiento
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.Automovil
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.MainActivity2
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.MainActivity3
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    var filtro = "NOMBRE"
    var listaIDs = ArrayList<String>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val spinner = arrayOf("Nombre", "Licencia","Domicilio")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item,spinner)
        adapter.setDropDownViewResource(R.layout.simple_spinner_item)
        binding.spinner1.adapter = adapter
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(binding.spinner1.selectedItemPosition == 0){
                    filtro = "NOMBRE"
                }
                if(binding.spinner1.selectedItemPosition == 1){
                    filtro = "LICENCIACOND"
                }
                if(binding.spinner1.selectedItemPosition == 2){
                    filtro = "DOMOCILIO"
                }
                if(binding.spinner1.selectedItemPosition == 3){
                    filtro = "MARCA"
                }
                if(binding.spinner1.selectedItemPosition == 4){
                    filtro = "MODELO"
                }
            }

        }


        mostrarDatosEnListView2()

        binding.insert.setOnClickListener {
            var arren = Arrendamiento(requireContext()) //ALUMNO ES CLASE CONTROLADOR = ADMON DE DATOS

            arren.nombre = binding.nombre.text.toString()
            arren.domicilio = binding.dom.text.toString()
            arren.licenciacond = binding.lic.text.toString()
            arren.idauto = binding.autom.text.toString().toInt()
            arren.fecha = "2022-01-10"

            val resultado = arren.insertar() //PARA MAINACTIVITY LA INSERCION ES ABSTRACTA
            if(resultado){
                Toast.makeText(requireContext(), "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatosEnListView2()
                binding.nombre.setText("")
                binding.dom.setText("")
                binding.lic.setText("")
                binding.autom.setText("0")
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            } //LA CLASE MAIN ACTIVITY ES VISTA! ES DECIR INTERFAZ GRAFICA
        }


        binding.filtrar.setOnClickListener {

            mostrarDatosEnListView3()
        }
        binding.todos.setOnClickListener {
            mostrarDatosEnListView2()
        }
        binding.opciones.setOnClickListener {
            mostrarDatosEnListView()
        }


        return root
    }

    fun mostrarDatosEnListView() {
        var listaAutos = Automovil(requireContext()).mostrarTodos()
        var ModeloAutos = ArrayList<String>()
        var MarcaAutos = ArrayList<String>()
        var kiloAutos = ArrayList<Int>()
        var cad = ArrayList<String>()
        var cad2 = ""

        listaIDs.clear()

        (0..listaAutos.size - 1).forEach {
            val al = listaAutos.get(it)

            ModeloAutos.add(al.modelo)
            MarcaAutos.add(al.marca)
            kiloAutos.add(al.kilometrage)

            listaIDs.add(al.idauto.toString())

            cad2 = listaIDs[it] + " " + ModeloAutos[it] + " " + MarcaAutos[it] + " " + kiloAutos[it]
            cad.add(cad2)
        }
        binding.lista2.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,cad)

        binding.lista2.setOnItemClickListener { adapterView, view, indicee, l ->
            val idautoslista = listaIDs.get(indicee)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Deseas seleccionar este auto?")
                .setPositiveButton("Seleccionar") { d, i ->
                    //var otraVentana = Intent(requireContext(), MainActivity2::class.java)
                    //otraVentana.putExtra("idauto",idautoslista)
                    //startActivity(otraVentana)
                    binding.autom.setText("${idautoslista}")
                }
                .setNeutralButton("Cerrar") { d, i -> }
                .show()
        }
    }
    fun mostrarDatosEnListView2(){
        var listaArrendamiento  = Arrendamiento(requireContext()).mostrarTodos()
        var nombreArrendamiento = ArrayList<String>()
        var domicilioArrendamiento = ArrayList<String>()
        var licenciaArrendamiento = ArrayList<String>()
        var IdsAutoArrendamiento = ArrayList<String>()
        var fechaArrendamiento = ArrayList<String>()
        var cad = ArrayList<String>()
        var cad2 =""

        listaIDs.clear()

        (0..listaArrendamiento.size-1).forEach {
            val ar = listaArrendamiento.get(it)
            nombreArrendamiento.add(ar.nombre)
            domicilioArrendamiento.add(ar.domicilio)
            licenciaArrendamiento.add(ar.licenciacond)
            IdsAutoArrendamiento.add(ar.idauto.toString())
            fechaArrendamiento.add(ar.fecha)
            listaIDs.add(ar.idarrenda.toString())
            cad2= nombreArrendamiento[it]+" "+domicilioArrendamiento[it] +" "+licenciaArrendamiento[it]+" "+IdsAutoArrendamiento[it]+ fechaArrendamiento[it]
            cad.add(cad2)
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,cad)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idarrenlista = listaIDs.get(indice)
            val arre = Arrendamiento(requireContext()).mostrarArreID(idarrenlista)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Qué desea hacer con \n Nombre: ${arre.nombre}")
                .setNegativeButton("Eliminar"){d,i->
                    arre.eliminar(idarrenlista)
                    mostrarDatosEnListView2()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(requireContext(), MainActivity3::class.java)
                    otraVentana.putExtra("idarrenda",idarrenlista)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    fun mostrarDatosEnListView3(){
        var listaArrendamiento  = Arrendamiento(requireContext()).mostrarTodos2(filtro,binding.clave.text.toString())
        var nombreArrendamiento = ArrayList<String>()
        var domicilioArrendamiento = ArrayList<String>()
        var licenciaArrendamiento = ArrayList<String>()
        var IdsAutoArrendamiento = ArrayList<String>()
        var fechaArrendamiento = ArrayList<String>()
        var cad = ArrayList<String>()
        var cad2 =""

        listaIDs.clear()

        (0..listaArrendamiento.size-1).forEach {
            val ar = listaArrendamiento.get(it)
            nombreArrendamiento.add(ar.nombre)
            domicilioArrendamiento.add(ar.domicilio)
            licenciaArrendamiento.add(ar.licenciacond)
            IdsAutoArrendamiento.add(ar.idauto.toString())
            fechaArrendamiento.add(ar.fecha)
            listaIDs.add(ar.idarrenda.toString())
            cad2= nombreArrendamiento[it]+" "+domicilioArrendamiento[it] +" "+licenciaArrendamiento[it]+" "+IdsAutoArrendamiento[it]+ fechaArrendamiento[it]
            cad.add(cad2)
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,cad)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idarrenlista = listaIDs.get(indice)
            val arre = Arrendamiento(requireContext()).mostrarArreID(idarrenlista)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Qué desea hacer con \n Nombre: ${arre.nombre}")
                .setNegativeButton("Eliminar"){d,i->
                    arre.eliminar(idarrenlista)
                    mostrarDatosEnListView2()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(requireContext(), MainActivity3::class.java)
                    otraVentana.putExtra("idarrenda",idarrenlista)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosEnListView2()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}