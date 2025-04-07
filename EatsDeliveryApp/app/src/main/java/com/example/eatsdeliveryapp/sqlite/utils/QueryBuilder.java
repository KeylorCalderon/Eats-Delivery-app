package com.example.eatsdeliveryapp.sqlite.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

//Clase para el query builder
public class QueryBuilder {

    //atributos necesarios
    private String table_name;
    private final ArrayList<String> fields;
    private final ArrayList<String> foreign_keys;
    private final ArrayList<String> composite_keys;

    //funcion que hace la solicitud
    public QueryBuilder() {
        fields = new ArrayList<>();
        foreign_keys = new ArrayList<>();
        composite_keys = new ArrayList<>();
    }

    /**
     * Ajusta el nombre de la tabla
     * @param table_name Nombre de la tabla
     * @return Este constructor
     */
    public QueryBuilder setTable(String table_name) {
        this.table_name = table_name;
        return this;
    }

    /**
     * Agrega un campo
     * @param field_name Nombre de la columna
     * @param type Tipo de dato de la columna
     * @return Este constructor
     */
    public QueryBuilder addField(String field_name, FieldType type) {
        return addField(field_name, type, false, false, false);
    }

    /**
     * Agrega un campo
     * @param field_name Nombre de la columna
     * @param type Tipo de dato de la columna
     * @param notNull Indica si no permite nulos
     * @return Este constructor
     */
    public QueryBuilder addField(String field_name, FieldType type, boolean notNull) {
        return addField(field_name, type, notNull, false, false);
    }

    /**
     * Agrega un campo
     * @param field_name Nombre de la columna
     * @param type Tipo de dato de la columna
     * @param notNull Indica si no permite nulos
     * @param primaryKey Indica si es una llave primaria
     * @return Este constructor
     */
    public QueryBuilder addField(String field_name, FieldType type, boolean notNull, boolean primaryKey) {
        return addField(field_name, type, notNull, primaryKey, false);
    }

    /**
     * Agrega un campo
     * @param field_name Nombre de la columna
     * @param type Tipo de dato de la columna
     * @param notNull Indica si no permite nulos
     * @param primaryKey Indica si es una llave primaria
     * @param autoincrement Indica si la columna es autoincrementable
     * @return Este constructor
     */
    public QueryBuilder addField(String field_name, FieldType type, boolean notNull, boolean primaryKey, boolean autoincrement) {
        fields.add(String.format("%s %s%s%s%s", field_name, type, notNull ? " NOT NULL" : "", primaryKey ? " PRIMARY KEY" : "", autoincrement ? " AUTOINCREMENT" : ""));
        return this;
    }

    /**
     * Agrega una llave foránea
     * @param field_name Nombre de la columna de la tabla
     * @param fk_table_name Nombre de la tabla a la que pertenece la llave
     * @param fk_field_name Nombre de la columna de la tabla a la que pertenece la llave
     * @return Este constructor
     */
    public QueryBuilder addForeignKey(String field_name, String fk_table_name, String fk_field_name) {
        foreign_keys.add(String.format("FOREIGN KEY(%s) REFERENCES %s(%s)", field_name, fk_table_name, fk_field_name));
        return this;
    }

    public QueryBuilder addCompositeKey(String pk_field_1, String pk_field_2) {
        composite_keys.add(String.format("PRIMARY KEY(%s, %s)", pk_field_1, pk_field_2));
        return this;
    }

    /**
     * Crea y retorna la sentencia SQL
     * @return Sentencia SQL
     */
    public String finish() {
        ArrayList<String> fields = new ArrayList<>();
        fields.addAll(this.fields);
        fields.addAll(this.foreign_keys);
        fields.addAll(this.composite_keys);

        return String.format(
                "CREATE TABLE %s (%s)",
                table_name,
                TextUtils.join(", ", fields)
        );
    }
}
