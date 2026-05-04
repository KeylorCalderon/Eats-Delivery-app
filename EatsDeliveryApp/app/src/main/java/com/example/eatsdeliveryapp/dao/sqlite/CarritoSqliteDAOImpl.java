package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.CarritoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Carrito;
import com.example.eatsdeliveryapp.sqlite.contracts.CarritoContract;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarritoSqliteDAOImpl extends GenericSqliteDAOImpl<Carrito, Integer> implements CarritoDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_CARRITOS = String.format("select * from %s;", CarritoContract.TABLE_NAME);

    public CarritoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Carrito> findAll() {
        try {
            String sql = String.format(SQL_SELECT_CARRITOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Carrito cliente, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(CarritoContract.COLUMN.ID_CLIENTE, cliente.getIdCliente());
        values.put(CarritoContract.COLUMN.FECHA_CREACION, cliente.getFechaCreacion());
        values.put(CarritoContract.COLUMN.FECHA_MODIFICACION, cliente.getFechaModificacion());
        return db.insert(CarritoContract.TABLE_NAME, null, values);
    }

    @Override
    protected Carrito cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(CarritoContract.COLUMN.ID));
        int IdCliente = cursor.getInt(cursor.getColumnIndex(CarritoContract.COLUMN.ID_CLIENTE));
        String FechaCreacion = cursor.getString(cursor.getColumnIndex(CarritoContract.COLUMN.FECHA_CREACION));
        String FechaModificacion = cursor.getString(cursor.getColumnIndex(CarritoContract.COLUMN.FECHA_MODIFICACION));
        return new Carrito(Id, IdCliente, FechaCreacion, FechaModificacion);
    }

    @Override
    protected List<Carrito> cursorToList(Cursor cursor) {
        List<Carrito> carritos = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                carritos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return carritos;
    }

}
