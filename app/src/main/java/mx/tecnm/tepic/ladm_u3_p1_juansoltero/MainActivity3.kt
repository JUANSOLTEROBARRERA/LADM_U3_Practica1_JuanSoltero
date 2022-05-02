package mx.tecnm.tepic.ladm_u3_p1_juansoltero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.tepic.ladm_u3_p1_juansoltero.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding:ActivityMain3Binding
    var idArre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)



        idArre = this.intent.extras!!.getString("idarrenda")!!
        val arre = Arrendamiento(this).mostrarArreID(idArre)

        binding.nombre.setText(arre.nombre)
        binding.dom.setText(arre.domicilio)
        binding.lic.setText(arre.licenciacond)


        binding.actualizar.setOnClickListener {
            var arre = Arrendamiento(this)

            arre.nombre = binding.nombre.text.toString()
            arre.domicilio = binding.dom.text.toString()
            arre.licenciacond = binding.lic.text.toString()

            val respuesta = arre.actualizar(idArre)

            if(respuesta){
                Toast.makeText(this,"SE ACTUALIZO CORRECTAMENTE", Toast.LENGTH_LONG)
                    .show()
                binding.nombre.setText("")
                binding.dom.setText("")
                binding.lic.setText("")
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