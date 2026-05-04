package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.PedidoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DireccionSqliteDAOImpl extends GenericSqliteDAOImpl<Direccion, Integer> implements DireccionDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_DIRECCION = String.format("select * from %s;", DireccionContract.TABLE_NAME);

    public DireccionSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Direccion> findAll() {
        try {
            String sql = String.format(SQL_SELECT_DIRECCION);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Direccion direccion, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        //Preparamos los datos
        ContentValues values = new ContentValues();
        values.put("Distrito", direccion.getDistrito());
        values.put("Canton", direccion.getCanton());
        values.put("Provincia", direccion.getProvincia());
        values.put("DireccionExacta", direccion.getDireccionExacta());

        return db.insert(DireccionContract.TABLE_NAME, null, values);
    }

    @Override
    protected Direccion cursorToEntity(Cursor cursor) {

        //int id = cursor.getInt(cursor.getColumnIndex(DireccionContract.COLUMN.ID));
        String distrito = cursor.getString(cursor.getColumnIndex(DireccionContract.COLUMN.NOMBRE_DISTRITO));
        String canton = cursor.getString(cursor.getColumnIndex(DireccionContract.COLUMN.NOMBRE_CANTON));
        String provincia = cursor.getString(cursor.getColumnIndex(DireccionContract.COLUMN.NOMBRE_PROVINCIA));
        String direccion = cursor.getString(cursor.getColumnIndex(DireccionContract.COLUMN.DIRECCION_EXACTA));

        return new Direccion(distrito, canton, provincia, direccion);
    }

    @Override
    protected List<Direccion> cursorToList(Cursor cursor) {
        List<Direccion> direcciones = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                direcciones.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return direcciones;
    }

    @Override
    public int ActualizarDireccion(Direccion direccion) {

        int error=-1;
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();

            values.put("Distrito", direccion.getDistrito());
            values.put("Canton", direccion.getCanton());
            values.put("Provincia", direccion.getProvincia());
            values.put("DireccionExacta", direccion.getDireccionExacta());

            db.update(DireccionContract.TABLE_NAME, values, DireccionContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(direccion.getId())});
            error =2;
        }catch (Exception e) {
            e.toString();
        }
        return error;
    }

    @Override
    public Direccion getDireccion(int id){

        Direccion direccion = new Direccion();

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(id)};
            Cursor c = db.rawQuery("SELECT Id, Provincia, Canton, Distrito, DireccionExacta FROM " +
                    DireccionContract.TABLE_NAME + " WHERE Id =?", args);

            c.moveToFirst();

            direccion.setId(c.getInt(0));
            direccion.setProvincia(c.getString(1));
            direccion.setCanton(c.getString(2));
            direccion.setDistrito(c.getString(3));
            direccion.setDireccionExacta(c.getString(4));

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return direccion;
    }
}
