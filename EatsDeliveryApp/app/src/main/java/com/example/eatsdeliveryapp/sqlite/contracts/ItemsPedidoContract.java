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

public class ItemsPedidoContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "ItemsPedido";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private ItemsPedidoContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(ItemsPedidoContract.TABLE_NAME)
            .addField(COLUMN.ID_PRODUCTO, FieldType.INTEGER, true)
            .addField(COLUMN.ID_PEDIDO, FieldType.INTEGER, true)
            .addField(COLUMN.CANTIDAD, FieldType.INTEGER, true)
            .addField(COLUMN.ACTIVO, FieldType.INTEGER, true)
            .addCompositeKey(COLUMN.ID_PEDIDO, COLUMN.ID_PRODUCTO)
            .addForeignKey(COLUMN.ID_PEDIDO, PedidoContract.TABLE_NAME, PedidoContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_PRODUCTO, ProductoContract.TABLE_NAME, ProductoContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemsPedidoContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID_PRODUCTO = "IdProducto";
        public static final String ID_PEDIDO = "IdPedido";
        public static final String CANTIDAD = "Cantidad";
        public static final String ACTIVO = "Activo";
    }

}
