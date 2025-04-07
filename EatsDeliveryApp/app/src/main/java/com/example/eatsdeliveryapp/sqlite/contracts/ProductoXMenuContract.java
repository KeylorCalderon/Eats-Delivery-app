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

public class ProductoXMenuContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "ProductoXMenu";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private ProductoXMenuContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(ProductoXMenuContract.TABLE_NAME)
            .addField(COLUMN.ID_PRODUCTO, FieldType.INTEGER, true)
            .addField(COLUMN.ID_MENU, FieldType.INTEGER, true)
            .addField(COLUMN.CANTIDAD_STOCK, FieldType.INTEGER, true)
            .addCompositeKey(COLUMN.ID_PRODUCTO, COLUMN.ID_MENU)
            .addForeignKey(COLUMN.ID_PRODUCTO, ProductoContract.TABLE_NAME, ProductoContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_MENU, MenuContract.TABLE_NAME, MenuContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductoXMenuContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID_PRODUCTO = "IdProducto";
        public static final String ID_MENU = "IdMenu";
        public static final String CANTIDAD_STOCK = "CantidadStock";
    }

}
