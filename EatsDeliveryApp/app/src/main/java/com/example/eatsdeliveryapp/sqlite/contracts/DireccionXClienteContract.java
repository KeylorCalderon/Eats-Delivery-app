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

public class DireccionXClienteContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "DireccionXCliente";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private DireccionXClienteContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(DireccionXClienteContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_CLIENTE, FieldType.INTEGER, true)
            .addField(COLUMN.ID_DIRECCION, FieldType.INTEGER, true)
            .addField(COLUMN.ACTIVO, FieldType.TEXT, true)
            .addForeignKey(COLUMN.ID_CLIENTE, ClienteContract.TABLE_NAME, ClienteContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_DIRECCION, DireccionContract.TABLE_NAME, DireccionContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DireccionXClienteContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_CLIENTE = "IdCliente";
        public static final String ID_DIRECCION = "IdDireccion";
        public static final String ACTIVO = "Activo";
    }

}
