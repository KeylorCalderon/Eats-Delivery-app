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

public class CuentaAdministradorContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "CuentaAdministrador";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private CuentaAdministradorContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(CuentaAdministradorContract.TABLE_NAME)
            .addField(COLUMN.ID_LABORAL, FieldType.TEXT, true, true)
            .addField(COLUMN.CONTRASENA, FieldType.TEXT, true)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CuentaAdministradorContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID_LABORAL = "IdLaboral";
        public static final String CONTRASENA = "Contrasena";
    }
}
