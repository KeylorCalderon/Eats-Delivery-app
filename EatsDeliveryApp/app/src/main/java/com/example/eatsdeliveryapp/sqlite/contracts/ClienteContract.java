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
public class ClienteContract {

    // Nombre de la tabla
    public static String TABLE_NAME = "Cliente";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private ClienteContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(ClienteContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.NOMBRE_USUARIO, FieldType.TEXT, true)
            .addField(COLUMN.NOMBRE, FieldType.TEXT, true)
            .addField(COLUMN.PRIMER_APELLIDO, FieldType.TEXT, true)
            .addField(COLUMN.SEGUNDO_APELLIDO, FieldType.TEXT, true)
            .addField(COLUMN.CORREO, FieldType.TEXT, true)
            .addForeignKey(COLUMN.NOMBRE_USUARIO, CuentaClienteContract.TABLE_NAME, CuentaClienteContract.COLUMN.NOMBRE_USUARIO)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ClienteContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String NOMBRE_USUARIO = "NombreUsuario";
        public static final String NOMBRE = "Nombre";
        public static final String PRIMER_APELLIDO = "PrimerApellido";
        public static final String SEGUNDO_APELLIDO = "SegundoApellido";
        public static final String CORREO = "Correo";
    }
}
