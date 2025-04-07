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

public class ProductoContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "Producto";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private ProductoContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(ProductoContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_RESTAURANTE, FieldType.INTEGER, true)
            .addField(COLUMN.NOMBRE, FieldType.TEXT, true)
            .addField(COLUMN.FOTO, FieldType.BLOB, true)
            .addField(COLUMN.DESCRIPCION, FieldType.TEXT, true)
            .addField(COLUMN.PRECIO, FieldType.REAL, true)
            .addField(COLUMN.CANTIDAD, FieldType.INTEGER, true)
            .addField(COLUMN.ACTIVO, FieldType.INTEGER, true)
            .addForeignKey(COLUMN.ID_RESTAURANTE, RestauranteContract.TABLE_NAME, RestauranteContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductoContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_RESTAURANTE = "IdRestaurante";
        public static final String NOMBRE = "Nombre";
        public static final String FOTO = "Foto";
        public static final String DESCRIPCION = "Descripcion";
        public static final String PRECIO = "Precio";
        public static final String CANTIDAD = "Cantidad";
        public static final String ACTIVO = "Activo";
    }


}
