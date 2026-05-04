package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.CuentaAdministradorDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.CuentaAdministrador;
import com.example.eatsdeliveryapp.sqlite.contracts.AdministradorContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaAdministradorContract;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuentaAdministradorSqliteDAOlmpl extends GenericSqliteDAOImpl<CuentaAdministrador, Integer>
                                                implements CuentaAdministradorDAO {
    private final DBProperties dbProperties;
    private static final String SQL_SELECT_CUENTAS_ADMIN = String.format("select * from %s;", CuentaAdministradorContract.TABLE_NAME);


    public CuentaAdministradorSqliteDAOlmpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<CuentaAdministrador> findAll() {
        try {
            String sql = String.format(SQL_SELECT_CUENTAS_ADMIN);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(CuentaAdministrador cuentaAdministrador, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(CuentaAdministradorContract.COLUMN.ID_LABORAL, cuentaAdministrador.getIdLaboral());
        values.put(CuentaAdministradorContract.COLUMN.CONTRASENA, cuentaAdministrador.getContrasenna());

        return db.insert(CuentaAdministradorContract.TABLE_NAME, null, values);
    }

    @Override
    protected CuentaAdministrador cursorToEntity(Cursor cursor) {

        String idLaboral = cursor.getString(cursor.getColumnIndex(CuentaAdministradorContract.COLUMN.ID_LABORAL));
        String contrasena = cursor.getString(cursor.getColumnIndex(CuentaAdministradorContract.COLUMN.CONTRASENA));

        return new CuentaAdministrador(idLaboral, contrasena);
    }

    @Override
    protected List<CuentaAdministrador> cursorToList(Cursor cursor) {
        List<CuentaAdministrador> cuentas = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                cuentas.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return cuentas;
    }


    @Override
    public int[] verificarAdmin(String idLaboral, String Contrasena) {
        int tipo[]= new int [2];
        tipo[0]=-1;
        try {


            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String[] args = new String[] {idLaboral};
            Cursor c = db.rawQuery("SELECT Contrasena FROM "+CuentaAdministradorContract.TABLE_NAME+
                    " WHERE IdLaboral=? ", args);

            c.moveToFirst();
            String contrasenaAux=c.getString(0);

            //En caso correcto, se activa la bandera
            if(contrasenaAux.equals(Contrasena)) {

                //Captura el id del admin
                c = db.rawQuery("SELECT Id FROM "+ AdministradorContract.TABLE_NAME+
                        " WHERE IdLaboral=? ", args);
                c.moveToFirst();
                tipo[0] = c.getInt(0);

                //Captura el idPuesto
                c = db.rawQuery("SELECT IdPuesto FROM "+ AdministradorContract.TABLE_NAME+
                        " WHERE IdLaboral=? ", args);
                c.moveToFirst();
                tipo[1] = c.getInt(0);
            }

        }catch (Exception e) {
            e.toString();
        }
        return tipo;
    }

}
