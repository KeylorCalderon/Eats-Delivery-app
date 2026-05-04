package com.example.eatsdeliveryapp.sqlite.contracts;


import android.provider.BaseColumns;

import com.example.eatsdeliveryapp.sqlite.utils.FieldType;
import com.example.eatsdeliveryapp.sqlite.utils.QueryBuilder;

/**
 * Esta clase es un "Contrato". Permite reutilizar y mantener los nombres en un solo lugar.
 * Es como un esquema de una tabla
 *
 * https://developer.android.com/training/data-storage/sqlite?hl=es-419#java
 */

public class CantonContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "Canton";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private CantonContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(CantonContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_PROVINCIA, FieldType.INTEGER, true)
            .addField(COLUMN.NOMBRE, FieldType.TEXT, true)
            .addForeignKey(COLUMN.ID, ProvinciaContract.TABLE_NAME, ProvinciaContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CantonContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_PROVINCIA = "IdProvincia";
        public static final String NOMBRE = "Nombre";
    }


}
