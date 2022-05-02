package mx.tecnm.tepic.ladm_u3_p1_juansoltero.ui.dashboard

import android.R
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.Automovil
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.BaseDatos
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.MainActivity2
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    var filtro = "MODELO"
    var listaIDs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val spinner = arrayOf("Modelo", "Marca","Kilometraje")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item,spinner)
        adapter.setDropDownViewResource(R.layout.simple_spinner_item)
        binding.spinner1.adapter = adapter
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(binding.spinner1.selectedItemPosition == 0){
                    filtro = "MODELO"
                }
                if(binding.spinner1.selectedItemPosition == 1){
                    filtro = "MARCA"
                }
                if(binding.spinner1.selectedItemPosition == 2){
                    filtro = "KILOMETRAGE"
                }
            }
        }
        //codigo de bd

        binding.insert.setOnClickListener {
            var auto = Automovil(requireContext()) //ALUMNO ES CLASE CONTROLADOR = ADMON DE DATOS

            auto.modelo = binding.modelo.text.toString()
            auto.marca = binding.marca.text.toString()
            auto.kilometrage = binding.kilo.text.toString().toInt()

            val resultado = auto.insertar() //PARA MAINACTIVITY LA INSERCION ES ABSTRACTA
            if(resultado){
                Toast.makeText(requireContext(), "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatosEnListView()
                binding.modelo.setText("")
                binding.marca.setText("")
                binding.kilo.setText("")
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            } //LA CLASE MAIN ACTIVITY ES VISTA! ES DECIR INTERFAZ GRAFICA
        }

        //
        return root
    }
    fun mostrarDatosEnListView(){
        var listaAutos = Automovil(requireContext()).mostrarTodos()
        var ModeloAutos = ArrayList<String>()
        var MarcaAutos = ArrayList<String>()
        var kiloAutos = ArrayList<Int>()
        var cad = ArrayList<String>()
        var cad2 =""

        listaIDs.clear()

        (0..listaAutos.size-1).forEach {
            val al = listaAutos.get(it)

            ModeloAutos.add(al.modelo)
            MarcaAutos.add(al.marca)
            kiloAutos.add(al.kilometrage)

            listaIDs.add(al.idauto.toString())

            cad2= listaIDs[it]+" "+ModeloAutos[it]+" "+MarcaAutos[it] +" "+kiloAutos[it]
            cad.add(cad2)
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,cad)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idautoslista = listaIDs.get(indice)
            val auto = Automovil(requireContext()).mostrarAutoID(idautoslista)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Qué desea hacer con \n Modelo: ${auto.modelo}, \nMarca: ${auto.marca}, \nKilometrage: ${auto.kilometrage}?")
                .setNegativeButton("Eliminar"){d,i->
                    auto.eliminar(idautoslista)
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(requireContext(), MainActivity2::class.java)
                    otraVentana.putExtra("idauto",idautoslista)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
        fun mostrarDatosEnListView2() {
            var listaAutos =
                Automovil(requireContext()).mostrarTodos2(filtro, binding.clave.text.toString())
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

                cad2 =
                    listaIDs[it] + " " + ModeloAutos[it] + " " + MarcaAutos[it] + " " + kiloAutos[it]
                cad.add(cad2)
            }

            binding.lista.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, cad)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idautoslista = listaIDs.get(indice)
                val auto = Automovil(requireContext()).mostrarAutoID(idautoslista)

                AlertDialog.Builder(requireContext())
                    .setTitle("ATENCION")
                    .setMessage("¿Qué desea hacer con \n Modelo: ${auto.modelo}, \nMarca: ${auto.marca}, \nKilometrage: ${auto.kilometrage}?")
                    .setNegativeButton("Eliminar") { d, i ->
                        auto.eliminar(idautoslista)
                        mostrarDatosEnListView()
                    }
                    .setPositiveButton("Actualizar") { d, i ->
                        var otraVentana = Intent(requireContext(), MainActivity2::class.java)
                        otraVentana.putExtra("idauto", idautoslista)
                        startActivity(otraVentana)
                    }
                    .setNeutralButton("Cerrar") { d, i -> }
                    .show()
            }
        }
            binding.filtrar.setOnClickListener {
                mostrarDatosEnListView2()
            }
            binding.todos.setOnClickListener {
                mostrarDatosEnListView()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosEnListView()
    }
}