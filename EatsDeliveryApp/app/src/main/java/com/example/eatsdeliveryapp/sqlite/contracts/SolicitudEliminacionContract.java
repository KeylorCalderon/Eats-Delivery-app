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

public class SolicitudEliminacionContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "SolicitudEliminacion";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private SolicitudEliminacionContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(SolicitudEliminacionContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_RESTAURANTE, FieldType.INTEGER, true)
            .addField(COLUMN.ID_ENCARGADO, FieldType.INTEGER, true)
            .addField(COLUMN.ACTIVO, FieldType.INTEGER, true)
            .addForeignKey(COLUMN.ID_RESTAURANTE, RestauranteContract.TABLE_NAME, RestauranteContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_ENCARGADO, AdministradorContract.TABLE_NAME, AdministradorContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SolicitudEliminacionContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_RESTAURANTE = "IdRestaurante";
        public static final String ID_ENCARGADO = "IdEncargado";
        public static final String ACTIVO = "Activo";
    }

}
