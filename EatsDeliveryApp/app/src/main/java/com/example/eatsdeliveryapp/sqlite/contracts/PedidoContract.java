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

public class PedidoContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "Pedido";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private PedidoContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(PedidoContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_ESTADO, FieldType.TEXT, true)
            .addField(COLUMN.ID_DIRECCION, FieldType.INTEGER, true)
            .addField(COLUMN.ID_CLIENTE, FieldType.INTEGER, true)
            .addField(COLUMN.FECHA, FieldType.TEXT, true)
            .addField(COLUMN.TOTAL_PAGADO, FieldType.REAL, true)
            .addForeignKey(COLUMN.ID_CLIENTE, ClienteContract.TABLE_NAME, ClienteContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PedidoContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_DIRECCION = "IdDireccion";
        public static final String ID_ESTADO = "IdEstado";
        //En curso
        //Cancelado
        //Entregado
        public static final String ID_CLIENTE = "IdCliente";
        public static final String FECHA = "Fecha";
        public static final String TOTAL_PAGADO = "TotalPagado";
    }

}
