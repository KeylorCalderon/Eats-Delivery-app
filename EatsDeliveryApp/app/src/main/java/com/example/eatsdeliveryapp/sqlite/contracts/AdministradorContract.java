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

public class AdministradorContract {

    // Nombre de la tabla
    public static final String TABLE_NAME = "Administrador";

    // El constructor privado nos permite evitar que esta clase sea instanciada por accidente
    private AdministradorContract() {}

    // Definicion de las columnas de la tabla
    public static final String SQL_CREATE_ENTRIES = new QueryBuilder()
            .setTable(AdministradorContract.TABLE_NAME)
            .addField(COLUMN.ID, FieldType.INTEGER, true, true, true)
            .addField(COLUMN.ID_RESTAURANTE, FieldType.INTEGER, true)
            .addField(COLUMN.ID_LABORAL, FieldType.TEXT, true)
            .addField(COLUMN.ID_PUESTO, FieldType.INTEGER, true)
            .addField(COLUMN.CEDULA, FieldType.INTEGER, true)
            .addField(COLUMN.NOMBRE, FieldType.TEXT, true)
            .addField(COLUMN.PRIMER_APELLIDO, FieldType.TEXT, true)
            .addField(COLUMN.SEGUNDO_APELLIDO, FieldType.TEXT, true)
            .addForeignKey(COLUMN.ID_RESTAURANTE, RestauranteContract.TABLE_NAME, RestauranteContract.COLUMN.ID)
            .addForeignKey(COLUMN.ID_LABORAL, CuentaAdministradorContract.TABLE_NAME, CuentaAdministradorContract.COLUMN.ID_LABORAL)
            .addForeignKey(COLUMN.ID_PUESTO, PuestoContract.TABLE_NAME, PuestoContract.COLUMN.ID)
            .finish();

    // Instruccion SQL para eliminar la tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AdministradorContract.TABLE_NAME + ";";

    // Clase interna que define el contenido de la tabla
    public static class COLUMN implements BaseColumns {
        public static final String ID = "Id";
        public static final String ID_RESTAURANTE = "IdRestaurante";
        public static final String ID_LABORAL = "IdLaboral";
        public static final String ID_PUESTO = "IdPuesto";
        public static final String CEDULA = "Cedula";
        public static final String NOMBRE = "Nombre";
        public static final String PRIMER_APELLIDO = "PrimerApellido";
        public static final String SEGUNDO_APELLIDO = "SegundoApellido";
    }

}
