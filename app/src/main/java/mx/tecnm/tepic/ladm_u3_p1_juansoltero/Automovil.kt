package mx.tecnm.tepic.ladm_u3_p1_juansoltero

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Automovil(este: Context) {
    private val este = este
    var idauto = 0
    var modelo = ""
    var marca = ""
    var kilometrage = 0
    private var err = ""


    fun mostrarTodos2(a:String, b:String): ArrayList<Automovil>{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err = ""
        var arreglo = ArrayList<Automovil>()
        try{
            val tabla = basedatos.readableDatabase
            var SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE ${a} = ${b}"
            if(a=="MODELO" || a=="MARCA") {
                SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE ${a} = '${b}'"
            }else{
                SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE ${a} = ${b}"
            }

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val auto = Automovil(este)
                    auto.idauto = cursor.getInt(0)
                    auto.modelo = cursor.getString(1)
                    auto.marca = cursor.getString(2)
                    auto.kilometrage = cursor.getInt(3)

                    arreglo.add(auto)
                }while (cursor.moveToNext())
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }

    fun insertar():Boolean{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        try{
            val tabla = basedatos.writableDatabase
            var datos = ContentValues()

            datos.put("MODELO", modelo)
            datos.put("MARCA",marca)
            datos.put("KILOMETRAGE",kilometrage)

            var respuesta = tabla.insert("AUTOMOVIL",null,datos)
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
    fun mostrarTodos(): ArrayList<Automovil>{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err = ""
        var arreglo = ArrayList<Automovil>()
        try{
            val tabla = basedatos.readableDatabase

            val SQLSELECT = "SELECT * FROM AUTOMOVIL"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val auto = Automovil(este)
                    auto.idauto = cursor.getInt(0)
                    auto.modelo = cursor.getString(1)
                    auto.marca = cursor.getString(2)
                    auto.kilometrage = cursor.getInt(3)

                    arreglo.add(auto)
                }while (cursor.moveToNext())
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }

    fun mostrarAutoID(idauto:String):Automovil {
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        val auto = Automovil(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE IDAUTO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(idauto))
            if(cursor.moveToFirst()){
                auto.modelo = cursor.getString(1)
                auto.marca = cursor.getString(2)
                auto.kilometrage = cursor.getInt(3)
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return auto
    }


    fun eliminar(idautoeliminar:String):Boolean{
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        try{
            var tabla = basedatos.writableDatabase
            val resultado = tabla.delete("AUTOMOVIL","IDAUTO=?", arrayOf(idautoeliminar))

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

            datosActualizados.put("MODELO",modelo)
            datosActualizados.put("MARCA",marca)
            datosActualizados.put("KILOMETRAGE",kilometrage)

            val respuesta = tabla.update("AUTOMOVIL", datosActualizados,
                "IDAUTO=?", arrayOf(actualizar))

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




    fun mostrarAutoModelo(clave:String):Automovil {
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        val auto = Automovil(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE MODELO=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(clave))
            if(cursor.moveToFirst()){
                auto.modelo = cursor.getString(1)
                auto.marca = cursor.getString(2)
                auto.kilometrage = cursor.getInt(3)
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return auto
    }
    fun mostrarAutoMarca(clave:String):Automovil {
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        val auto = Automovil(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE MARCA=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(clave))
            if(cursor.moveToFirst()){
                auto.modelo = cursor.getString(1)
                auto.marca = cursor.getString(2)
                auto.kilometrage = cursor.getInt(3)
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return auto
    }
    fun mostrarAutoKilo(clave:String):Automovil {
        val basedatos = BaseDatos(este, "RENTA_AUTOS",null,1)
        err=""
        val auto = Automovil(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM AUTOMOVIL WHERE KILOMETRAGE=?"

            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(clave))
            if(cursor.moveToFirst()){
                auto.modelo = cursor.getString(1)
                auto.marca = cursor.getString(2)
                auto.kilometrage = cursor.getInt(3)
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return auto
    }


}

