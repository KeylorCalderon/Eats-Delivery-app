package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.PuestoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Puesto;
import com.example.eatsdeliveryapp.sqlite.contracts.PuestoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuestoSqliteDAOImpl extends GenericSqliteDAOImpl<Puesto, Integer> implements PuestoDAO {


    private final DBProperties dbProperties;

    private static final String SQL_SELECT_PUESTOS= String.format("select * from %s;", PuestoContract.TABLE_NAME);

    public PuestoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Puesto> findAll() {
        try {
            String sql = String.format(SQL_SELECT_PUESTOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Puesto puesto, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();

        values.put(PuestoContract.COLUMN.NOMBRE, puesto.getNombre());

        return db.insert(PuestoContract.TABLE_NAME, null, values);
    }

    @Override
    protected Puesto cursorToEntity(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(PuestoContract.COLUMN.ID));
        String nombre =  cursor.getString(cursor.getColumnIndex(PuestoContract.COLUMN.NOMBRE));

        return new Puesto(id, nombre);
    }

    @Override
    protected List<Puesto> cursorToList(Cursor cursor) {
        List<Puesto> puestos = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                puestos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return puestos;
    }
}
