package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.TelefonoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.CuentaCliente;
import com.example.eatsdeliveryapp.model.Telefono;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.TelefonoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelefonoSqliteDAOImpl extends GenericSqliteDAOImpl<Telefono, Integer> implements TelefonoDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_TELEFONOS = String.format("select * from %s;", TelefonoContract.TABLE_NAME);

    public TelefonoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Telefono> findAll() {
        try {
            String sql = String.format(SQL_SELECT_TELEFONOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Telefono telefono, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(TelefonoContract.COLUMN.ID_CLIENTE, telefono.getIdCliente());
        values.put(TelefonoContract.COLUMN.NUMERO, telefono.getNumero());

        return db.insert(TelefonoContract.TABLE_NAME, null, values);
    }

    @Override
    protected Telefono cursorToEntity(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(TelefonoContract.COLUMN.ID));
        int idCliente = cursor.getInt(cursor.getColumnIndex(TelefonoContract.COLUMN.ID_CLIENTE));
        int numero = cursor.getInt(cursor.getColumnIndex(TelefonoContract.COLUMN.NUMERO));
        return new Telefono(id,idCliente, numero);
    }

    @Override
    protected List<Telefono> cursorToList(Cursor cursor) {
        List<Telefono> telefonos = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                telefonos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return telefonos;
    }
}
