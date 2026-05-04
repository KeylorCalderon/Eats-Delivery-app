package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.CuentaClienteDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.CuentaCliente;
import com.example.eatsdeliveryapp.sqlite.contracts.ClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaClienteContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuentaClienteSqliteDAOImpl extends GenericSqliteDAOImpl<CuentaCliente, Integer> implements CuentaClienteDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_CUENTAS = String.format("select * from %s;", CuentaClienteContract.TABLE_NAME);

    public CuentaClienteSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<CuentaCliente> findAll() {
        try {
            String sql = String.format(SQL_SELECT_CUENTAS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(CuentaCliente cuenta, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(CuentaClienteContract.COLUMN.NOMBRE_USUARIO, cuenta.getNombreUsuario());
        values.put(CuentaClienteContract.COLUMN.CONTRASENA, cuenta.getContrasena());

        return db.insert(CuentaClienteContract.TABLE_NAME, null, values);
    }

    @Override
    protected CuentaCliente cursorToEntity(Cursor cursor) {
        String NombreUsuario = cursor.getString(cursor.getColumnIndex(CuentaClienteContract.COLUMN.NOMBRE_USUARIO));
        String Contrasena = cursor.getString(cursor.getColumnIndex(CuentaClienteContract.COLUMN.CONTRASENA));
        return new CuentaCliente(NombreUsuario, Contrasena);
    }

    @Override
    protected List<CuentaCliente> cursorToList(Cursor cursor) {
        List<CuentaCliente> cuentas = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                cuentas.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return cuentas;
    }

    public int verificarCliente(String NombreUsuario, String Contrasena ){
        int id= -1;

        try {


            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String[] args = new String[] {NombreUsuario};
            Cursor c = db.rawQuery("SELECT Contrasena FROM "+CuentaClienteContract.TABLE_NAME+
                    " WHERE NombreUsuario=? ", args);

            c.moveToFirst();
            String contrasenaAux=c.getString(0);

            //En caso correcto, se activa la bandera
            if(contrasenaAux.equals(Contrasena)) {
                c = db.rawQuery("SELECT Id FROM "+ ClienteContract.TABLE_NAME+
                        " WHERE NombreUsuario=? ", args);
                c.moveToFirst();
                id = c.getInt(0);
            }

        }catch (Exception e) {
            e.toString();
        }
        return id;
    }

}
