package com.example.eatsdeliveryapp.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eatsdeliveryapp.MainActivity;

//Clase pública, aquí se almacenarán los procedimientos para las operaciones sobre las tablas
public class CRUD_BD extends AyudanteDB{

    //Atributos de la clase
    private Context context;
    private SQLiteDatabase DB;

    //Constructor recibe el context de la activity y una base de datos sqlite
    public CRUD_BD(@Nullable Context context, SQLiteDatabase DB) {
        super(context);
        this.context=context;
        this.DB=DB;
    }

    //Realiza la inserción el cliente
    public long insertarCliente(String NombreUsuario, String Nombre, String PrimerApellido,
                                String SegundoApellido, String Correo, String Contrasena, int telefono ){

        //variable para conservar el id generada
        long id=0;

        //try para conservar consistencia
        try {

            //Instanciamos la base
            AyudanteDB ayudanteDB = new AyudanteDB(context);
            SQLiteDatabase DB = ayudanteDB.getWritableDatabase();

            //Generamos los valores, para el insert, esto para la tabla cliente
            ContentValues values = new ContentValues();
            values.put("NombreUsuario", NombreUsuario);
            values.put("Nombre", Nombre);
            values.put("PrimerApellido", PrimerApellido);
            values.put("SegundoApellido", SegundoApellido);
            values.put("Correo", Correo);

            //Para la cuenta del cliente
            ContentValues values2 = new ContentValues();
            values2.put("NombreUsuario", NombreUsuario);
            values2.put("Contrasena", Contrasena);


            //Insertamos los valores en las dos tablas
            id = DB.insert(TABLE_CLIENTES, null, values);
            DB.insert(TABLE_CUENTACLIENTES, null, values2);

            //Contentvalues para la tabla telefono
            ContentValues values3 = new ContentValues();
            values3.put("IdCliente", (int)id);
            values3.put("Numero", telefono);

            //Inserción del teléfono
            DB.insert(TABLE_TELEFONO, null, values3);

        }catch (Exception e){
            e.toString();
        }
        return id;
    }

    //metodo que verifica los datos de login
    public boolean verificarCliente(String NombreUsuario, String Contrasena ){
        boolean correcto=false;
        try {
            Log.d("TAG","AQUIIIII" );

            //Realiza la consulta de la contraseña
            String[] args = new String[] {NombreUsuario};
            Cursor c = DB.rawQuery("SELECT Contrasena FROM "+TABLE_CUENTACLIENTES+
                    " WHERE NombreUsuario=? ", args);

            c.moveToFirst();
            String contrasenaAux=c.getString(0);

            //En caso correcto, se activa la bandera
            if(contrasenaAux.equals(Contrasena)){
                correcto=true;
            }

        }catch (Exception e) {
            e.toString();
            Log.d("TAG", "AQUIIIII ERROR " + e.toString());
        }
        return correcto;
    }

    //método que inserta las tarjetas
    public void insertarTarjetas(int idCliente, int numeroTarjeta, String nombreTitular, String fechaVence, int csv){

        //Try para la consistencia
        try {

            //Instanciamos la base
            AyudanteDB ayudanteDB = new AyudanteDB(context);
            SQLiteDatabase DB = ayudanteDB.getWritableDatabase();

            //Preparamos los datos
            ContentValues values = new ContentValues();
            values.put("IdCliente", idCliente);
            values.put("Numero", numeroTarjeta);
            values.put("NombreTitular", nombreTitular);
            values.put("FechaVencimiento", fechaVence);
            values.put("CodigoSeguridad", csv);

            //Realizamos el insert como tal
            DB.insert(TABLE_TARJETA, null, values);

        }catch (Exception e){
            e.toString();
        }
    }
}
