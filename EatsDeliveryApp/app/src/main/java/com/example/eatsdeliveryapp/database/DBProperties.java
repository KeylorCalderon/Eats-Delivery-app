package com.example.eatsdeliveryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.sqlite.EatsDeliveryDbHelper;

//Clase auxiliar del bdHelper, carga las propiedades de la base de datos sqlite
public class DBProperties {

    //variable privada final de dbHelper
    private final EatsDeliveryDbHelper eatsDeliveryDbHelper;

    //metodo para crear las propiedades de la base, el constructor
    public DBProperties(Context context) {
        eatsDeliveryDbHelper = new EatsDeliveryDbHelper(context);
    }

    //Para leer las tablas e informacion de la base
    public SQLiteDatabase getReadableConnection() {
        return eatsDeliveryDbHelper.getReadableDatabase();
    }

    //Para poder escribir en la base
    public SQLiteDatabase getWritableConnection() {
        return eatsDeliveryDbHelper.getWritableDatabase();
    }

    //Para eliminar esta base de datos
    public void dropDatabase() {
        eatsDeliveryDbHelper.dropDatabase();
    }
}