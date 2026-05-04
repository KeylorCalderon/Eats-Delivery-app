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

public class RestauranteXCategoriaContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "RestauranteXCategoria";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private RestauranteXCategoriaContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(RestauranteXCategoriaContract.TABLE_NAME)
            .addField(COLUMN.ID_RESTAURANTE, FieldType.INTEGER, true)
            .addField(COLUMN.ID_CATEGORIA, FieldType.INTEGER, true)
            .addCompositeKey(COLUMN.ID_CATEGORIA, COLUMN.ID_RESTAURANTE)
            .addForeignKey(COLUMN.ID_RESTAURANTE, RestauranteContract.TABLE_NAME, RestauranteContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_CATEGORIA, CategoriaContract.TABLE_NAME, CategoriaContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestauranteXCategoriaContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID_RESTAURANTE = "IdRestaurante";
        public static final String ID_CATEGORIA = "IdCategoria";
    }


}
