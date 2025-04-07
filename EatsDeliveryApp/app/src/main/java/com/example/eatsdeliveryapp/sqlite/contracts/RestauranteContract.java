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
public class RestauranteContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "Restaurante";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private RestauranteContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(RestauranteContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_DIRECCION, FieldType.INTEGER, true)
            .addField(COLUMN.NOMBRE, FieldType.TEXT, true)
            .addField(COLUMN.CATEGORIA, FieldType.TEXT, true)
            .addField(COLUMN.FOTO, FieldType.BLOB, true)
            .addField(COLUMN.ACTIVO, FieldType.INTEGER, true)
            .addForeignKey(COLUMN.ID_DIRECCION, DireccionContract.TABLE_NAME, DireccionContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestauranteContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_DIRECCION = "IdDireccion";
        public static final String NOMBRE = "Nombre";
        public static final String FOTO = "Foto";
        public static final String ACTIVO = "Activo";
        public static final String CATEGORIA = "Categoria";
    }

}
