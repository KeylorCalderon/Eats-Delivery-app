package com.example.eatsdeliveryapp.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//Clase publica que nos permite crear o referenciar la base de datos y sus tablas
public class AyudanteDB extends SQLiteOpenHelper {


    //atributos propios de la clase, se definen las tablas
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "EatsDelivery.db";
    public static final String TABLE_CLIENTES = "Cliente";
    public static final String TABLE_CUENTACLIENTES = "CuentaCliente";
    public static final String TABLE_TARJETA = "Tarjeta";
    public static final String TABLE_TELEFONO = "Telefono";


    //Constructor recibe el context de la activity que la llame
    public AyudanteDB(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    //Método por default
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Crea la tabla para los clientes
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CLIENTES + "(" +
                "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "NombreUsuario TEXT NOT NULL," +
                "Nombre TEXT NOT NULL," +
                "PrimerApellido TEXT NOT NULL," +
                "SegundoApellido TEXT NOT NULL," +
                "Correo TEXT NOT NULL)");

        //Tabla para la administración de las cuentas de usuario
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CUENTACLIENTES + "(" +
                "NombreUsuario TEXT NOT NULL PRIMARY KEY," +
                "Contrasena TEXT NOT NULL)");


        //Tabla para las tarjetas
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TARJETA+ "(" +
                "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "IdCliente INTEGER NOT NULL,"+
                "Numero INTEGER NOT NULL,"+
                "NombreTitular TEXT NOT NULL,"+
                "FechaVencimiento TEXT NOT NULL,"+
                "CodigoSeguridad INTEGER NOT NULL,"+
                "FOREIGN KEY (IdCliente) REFERENCES Cliente (Id))");


        //Tabla para almacenar los telefonos de los clientes
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TELEFONO+ "(" +
                "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "IdCliente INTEGER NOT NULL,"+
                "Numero INTEGER NOT NULL,"+
                "FOREIGN KEY (IdCliente) REFERENCES Cliente (Id))");

    }

    //actualizar la base, la recarga borra y hace
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CLIENTES);
        onCreate(sqLiteDatabase);

    }
}