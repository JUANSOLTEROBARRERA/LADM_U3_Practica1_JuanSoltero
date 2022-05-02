package mx.tecnm.tepic.ladm_u3_p1_juansoltero

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Arrendamiento(este: Context) {
    private val este = este
    var idauto = 0
    var nombre = ""
    var domicilio = ""
    var licenciacond = ""
    var fecha=""
    var idarrenda=0

    private var err = ""



    @RequiresApi(Build.VERSION_CODES.O)
    fun insertar():Boolean{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        try{
            val tabla = basedatos.writableDatabase
            var datos = ContentValues()

            datos.put("NOMBRE", nombre)
            datos.put("DOMOCILIO", domicilio)
            datos.put("LICENCIACOND", licenciacond)
            datos.put("IDAUTO", idauto)
            val current = LocalDateTime.now()
            val split = current.toString().split("T")
            datos.put("FECHA",split[0])


            var respuesta = tabla.insert("ARRENDAMIENTO",null,datos)
            if(respuesta == -1L){
                return false
            }
        }catch(err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Arrendamiento>{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err = ""
        var arreglo = ArrayList<Arrendamiento>()
        try{
            val tabla = basedatos.readableDatabase

            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val arre = Arrendamiento(este)
                    arre.idarrenda = cursor.getInt(0)
                    arre.nombre = cursor.getString(1)
                    arre.domicilio = cursor.getString(2)
                    arre.licenciacond = cursor.getString(3)
                    arre.idauto = cursor.getInt(4)
                    arre.fecha = cursor.getString(5)

                    arreglo.add(arre)
                }while (cursor.moveToNext())
            }
        }catch(err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }
    fun mostrarTodos2(a:String, b:String): ArrayList<Arrendamiento>{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err = ""
        var arreglo = ArrayList<Arrendamiento>()
        try{
            val tabla = basedatos.readableDatabase

            var SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE ${a} = ${b}"
            if(a=="NOMBRE" || a=="LICENCIACOND" || a=="DOMOCILIO") {

                System.out.println("${a} ${b}")
                SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE ${a} = '${b}'"
            }else{
                SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE ${a} = ${b}"
            }

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val arre = Arrendamiento(este)
                    arre.idarrenda = cursor.getInt(0)
                    arre.nombre = cursor.getString(1)
                    arre.domicilio = cursor.getString(2)
                    arre.licenciacond = cursor.getString(3)
                    arre.idauto = cursor.getInt(4)
                    arre.fecha = cursor.getString(5)

                    arreglo.add(arre)
                }while (cursor.moveToNext())
            }
        }catch(err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }
    fun mostrarArreID(idarre:String):Arrendamiento {
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        val arre = Arrendamiento(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDARRENDA=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idarre))
            if(cursor.moveToFirst()){
                arre.nombre = cursor.getString(1)
                arre.domicilio = cursor.getString(2)
                arre.licenciacond = cursor.getString(3)
                arre.idauto = cursor.getInt(4)
                arre.fecha = cursor.getString(5)
            }
        }catch(err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arre
    }

    fun eliminar(idarreneliminar:String):Boolean{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        try{
            var tabla = basedatos.writableDatabase
            val resultado = tabla.delete("ARRENDAMIENTO","IDARRENDA=?", arrayOf(idarreneliminar))

            if(resultado==0){
                return false
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }
    fun actualizar(actualizar:String):Boolean{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        try{
            var tabla = basedatos.writableDatabase
            val datosActualizados = ContentValues()

            datosActualizados.put("NOMBRE",nombre)
            datosActualizados.put("DOMOCILIO",domicilio)
            datosActualizados.put("LICENCIACOND",licenciacond)

            val respuesta = tabla.update("ARRENDAMIENTO", datosActualizados,
                "IDARRENDA=?", arrayOf(actualizar))

            if (respuesta==0){
                return false
            }

        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }
}