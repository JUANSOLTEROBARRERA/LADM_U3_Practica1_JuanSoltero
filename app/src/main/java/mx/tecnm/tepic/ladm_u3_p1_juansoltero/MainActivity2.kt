package mx.tecnm.tepic.ladm_u3_p1_juansoltero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    var idAuto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        idAuto = this.intent.extras!!.getString("idauto")!!
        val auto = Automovil(this).mostrarAutoID(idAuto)

        binding.modelo.setText(auto.modelo)
        binding.marca.setText(auto.marca)
        binding.kilo.setText(auto.kilometrage.toString())

        binding.actualizar.setOnClickListener {
            var auto = Automovil(this)

            auto.modelo = binding.modelo.text.toString()
            auto.marca = binding.marca.text.toString()
            auto.kilometrage = binding.kilo.text.toString().toInt()

            val respuesta = auto.actualizar(idAuto)

            if(respuesta){
                Toast.makeText(this,"SE ACTUALIZO CORRECTAMENTE", Toast.LENGTH_LONG)
                    .show()
                binding.modelo.setText("")
                binding.marca.setText("")
                binding.kilo.setText("")
            }else{
                AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("ERROR NO SE ACTUALIZO")
                    .show()
            }
        }
        binding.regresar.setOnClickListener {
            finish()
        }
    }
}