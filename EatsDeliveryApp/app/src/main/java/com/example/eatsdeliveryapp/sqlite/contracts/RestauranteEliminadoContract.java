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

public class RestauranteEliminadoContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "RestauranteEliminado";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private RestauranteEliminadoContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(RestauranteEliminadoContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_SOLICITUD, FieldType.INTEGER, true)
            .addField(COLUMN.ID_GERENTE, FieldType.INTEGER, true)
            .addField(COLUMN.FECHA_ELIMINADO, FieldType.TEXT, true)
            .addForeignKey(COLUMN.ID_SOLICITUD, SolicitudEliminacionContract.TABLE_NAME, SolicitudEliminacionContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_GERENTE, AdministradorContract.TABLE_NAME, AdministradorContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestauranteEliminadoContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_SOLICITUD = "IdSolicitud";
        public static final String ID_GERENTE = "IdGerente";
        public static final String FECHA_ELIMINADO = "FechaEliminado";
    }

}
