package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.AdministradorDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.sqlite.contracts.AdministradorContract;
import com.example.eatsdeliveryapp.sqlite.contracts.SolicitudEliminacionContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//Clase publica para las operaciones sobre la tabla administrador
public class AdministradorSqliteDAOImpl extends GenericSqliteDAOImpl<Administrador, Integer>
                                        implements AdministradorDAO {

    //variables propias
    private final DBProperties dbProperties;
    private static final String SQL_SELECT_ADMINS = String.format("select * from %s;", AdministradorContract.TABLE_NAME);

    //constructor
    public AdministradorSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    //Metodo que lista todos los administradores
    @Override
    public List<Administrador> findAll() {
        try {
            String sql = String.format(SQL_SELECT_ADMINS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    //metodo que inserta una fila en la tabla
    @Override
    public long save(Administrador administrador, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(AdministradorContract.COLUMN.ID_RESTAURANTE, administrador.getIdRestaurante());
        values.put(AdministradorContract.COLUMN.ID_LABORAL, administrador.getIdLaboral());
        values.put(AdministradorContract.COLUMN.ID_PUESTO, administrador.getIdPuesto());
        values.put(AdministradorContract.COLUMN.CEDULA, administrador.getCedula());
        values.put(AdministradorContract.COLUMN.NOMBRE, administrador.getNombre());
        values.put(AdministradorContract.COLUMN.PRIMER_APELLIDO, administrador.getPrimerApellido());
        values.put(AdministradorContract.COLUMN.SEGUNDO_APELLIDO, administrador.getSegundoApellido());

        return db.insert(AdministradorContract.TABLE_NAME, null, values);
    }

    //metodo que retorna un admin, segun el cursor
    @Override
    protected Administrador cursorToEntity(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(AdministradorContract.COLUMN.ID));
        int idRestaurante = cursor.getInt(cursor.getColumnIndex(AdministradorContract.COLUMN.ID_RESTAURANTE));
        String idLaboral = cursor.getString(cursor.getColumnIndex(AdministradorContract.COLUMN.ID_LABORAL));
        int idPuesto = cursor.getInt(cursor.getColumnIndex(AdministradorContract.COLUMN.ID_PUESTO));
        int cedula = cursor.getInt(cursor.getColumnIndex(AdministradorContract.COLUMN.CEDULA));
        String nombre = cursor.getString(cursor.getColumnIndex(AdministradorContract.COLUMN.NOMBRE));
        String apellido1 = cursor.getString(cursor.getColumnIndex(AdministradorContract.COLUMN.PRIMER_APELLIDO));
        String apellido2 = cursor.getString(cursor.getColumnIndex(AdministradorContract.COLUMN.SEGUNDO_APELLIDO));

        return new Administrador(id, idRestaurante, idLaboral, idPuesto, cedula, nombre, apellido1, apellido2);
    }


    //metodo que devuelve una lista de admins
    @Override
    protected List<Administrador> cursorToList(Cursor cursor) {
        List<Administrador> admins= new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                admins.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return admins;
    }

    //retorna un solo admin
    @Override
    public Administrador RetornarAdmin(int idAdmin) {

        Administrador admin = new Administrador();

        try {

            //realiza la consulta
            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(idAdmin)};
            Cursor c = db.rawQuery("SELECT Id, IdRestaurante, IdLaboral, IdPuesto, Cedula, Nombre, " +
                    "                   PrimerApellido, SegundoApellido FROM " +
                    AdministradorContract.TABLE_NAME +
                    " WHERE Id=? ", args);

            c.moveToFirst();

            admin.setId(c.getInt(0));
            admin.setIdRestaurante(c.getInt(1));
            admin.setIdLaboral(c.getString(2));
            admin.setIdPuesto(c.getInt(3));
            admin.setCedula(c.getInt(4));
            admin.setNombre(c.getString(5));
            admin.setPrimerApellido(c.getString(6));
            admin.setSegundoApellido(c.getString(7));


        }catch (Exception e) {
            e.toString();
        }

        return admin;   //lo retorna
    }

    @Override
    public int validaAdminDefault(int id) {

        int validar = 2;

        SQLiteDatabase db = this.dbProperties.getWritableConnection();

        String args[] = new String[]{Integer.toString(id)};

        Cursor c = db.rawQuery("SELECT * FROM "
                + AdministradorContract.TABLE_NAME +
                " WHERE Id=? ", args);

        if (c.moveToFirst()){
            validar = -1;
        }

        c.close();
        return validar;
    }
}
