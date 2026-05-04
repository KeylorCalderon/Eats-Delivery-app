package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.eatsdeliveryapp.dao.SolicitudEliminacionDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.model.SolicitudEliminacion;
import com.example.eatsdeliveryapp.sqlite.contracts.AdministradorContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.SolicitudEliminacionContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolicitudEliminacionDAOImpl extends GenericSqliteDAOImpl<SolicitudEliminacion, Integer> implements SolicitudEliminacionDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_SOLICITUDES= String.format("select * from %s where Activo = 1;", SolicitudEliminacionContract.TABLE_NAME);

    public SolicitudEliminacionDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<SolicitudEliminacion> findAll() {
        try {
            String sql = String.format(SQL_SELECT_SOLICITUDES);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(SolicitudEliminacion solicitudEliminacion, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        String args[] = new String[]{Integer.toString(solicitudEliminacion.getIdRestaurante())};

        Cursor c = db.rawQuery("SELECT * FROM "
                + SolicitudEliminacionContract.TABLE_NAME +
                " WHERE IdRestaurante=? ", args);

        if (c.moveToFirst()){
            c.close();
            return -1;
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(SolicitudEliminacionContract.COLUMN.ID_RESTAURANTE, solicitudEliminacion.getIdRestaurante());
        values.put(SolicitudEliminacionContract.COLUMN.ID_ENCARGADO, solicitudEliminacion.getIdEncargado());
        values.put(SolicitudEliminacionContract.COLUMN.ACTIVO, solicitudEliminacion.getActivo());

        return db.insert(SolicitudEliminacionContract.TABLE_NAME, null, values);
    }

    @Override
    protected SolicitudEliminacion cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(SolicitudEliminacionContract.COLUMN.ID));
        int IdRestaurante = cursor.getInt(cursor.getColumnIndex(SolicitudEliminacionContract.COLUMN.ID_RESTAURANTE));
        int IdEncargado = cursor.getInt(cursor.getColumnIndex(SolicitudEliminacionContract.COLUMN.ID_ENCARGADO));
        int Activo = cursor.getInt(cursor.getColumnIndex(SolicitudEliminacionContract.COLUMN.ACTIVO));

        return new SolicitudEliminacion(Id,IdRestaurante,IdEncargado,Activo);
    }

    @Override
    protected List<SolicitudEliminacion> cursorToList(Cursor cursor) {

        List<SolicitudEliminacion> solicitudEliminacion = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                solicitudEliminacion.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return solicitudEliminacion;
    }

    @Override
    public ArrayList<String> consultaSolicitudes(List<SolicitudEliminacion> solicitudes) {
        ArrayList<String> info = new ArrayList<>();

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String args[];

            for(int i=0; i < solicitudes.size(); i++){

                args = new String[]{Integer.toString(solicitudes.get(i).getIdEncargado())};

                Cursor c2 = db.rawQuery("SELECT Nombre,PrimerApellido FROM "
                        + AdministradorContract.TABLE_NAME +
                        " WHERE Id=? ", args);
                c2.moveToFirst();

                args = new String[]{Integer.toString(solicitudes.get(i).getIdRestaurante())};

                Cursor c3 = db.rawQuery("SELECT Nombre FROM "
                        + RestauranteContract.TABLE_NAME +
                        " WHERE Id=? ", args);
                c3.moveToFirst();

                String datos = c2.getString(0)+" "+c2.getString(1)+" solicito eliminar. \n"+
                        "El restaurante: "+c3.getString(0);

                info.add(datos);

                c2.close();
                c3.close();
            }

        }catch (Exception e) {
            e.toString();
        }
        return info;
    }

    @Override
    public ArrayList<byte[]> consultarImagenes(List<SolicitudEliminacion> solicitudes) {
        ArrayList<byte[]> imagenes = new ArrayList<>();

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String args[];

            for(int i=0; i < solicitudes.size(); i++){

                args = new String[]{Integer.toString(solicitudes.get(i).getIdRestaurante())};

                Cursor c3 = db.rawQuery("SELECT Foto FROM "
                        + RestauranteContract.TABLE_NAME +
                        " WHERE Id=? ", args);
                c3.moveToFirst();

                imagenes.add(c3.getBlob(0));

                c3.close();
            }

        }catch (Exception e) {
            e.toString();
        }
        return imagenes;
    }

    @Override
    public void EliminarSolicitud(int id) {

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(SolicitudEliminacionContract.COLUMN.ACTIVO, 0);
            db.update(SolicitudEliminacionContract.TABLE_NAME, values, SolicitudEliminacionContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

        }catch (Exception e) {
            e.toString();
        }
    }
}
