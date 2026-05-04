package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.DireccionXClienteDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.DireccionXCliente;
import com.example.eatsdeliveryapp.sqlite.contracts.ClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionXClienteContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Clase publica para las operaciones sobre la tabla direccionXCliente
public class DireccionXClienteSqliteDAOImpl  extends GenericSqliteDAOImpl<DireccionXCliente, Integer> implements DireccionXClienteDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_DIRECCIONXCLIENTE = String.format("select * from %s;", DireccionXClienteContract.TABLE_NAME);

    public DireccionXClienteSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<DireccionXCliente> findAll() {
        try {
            String sql = String.format(SQL_SELECT_DIRECCIONXCLIENTE);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(DireccionXCliente direccionXCliente, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        //Preparamos los datos
        ContentValues values = new ContentValues();
        values.put("IdCliente", direccionXCliente.geIdCliente());
        values.put("IdDireccion", direccionXCliente.getIdDireccion());
        values.put("Activo", direccionXCliente.getActivo());

        return db.insert(DireccionXClienteContract.TABLE_NAME, null, values);
    }

    @Override
    protected DireccionXCliente cursorToEntity(Cursor cursor) {

        //int id = cursor.getInt(cursor.getColumnIndex(DireccionContract.COLUMN.ID));
        int idCliente = cursor.getInt(cursor.getColumnIndex(DireccionXClienteContract.COLUMN.ID_CLIENTE));
        int idDireccion = cursor.getInt(cursor.getColumnIndex(DireccionXClienteContract.COLUMN.ID_DIRECCION));
        String activo = cursor.getString(cursor.getColumnIndex(DireccionXClienteContract.COLUMN.ACTIVO));

        return new DireccionXCliente(idCliente, idDireccion, activo);
    }

    @Override
    protected List<DireccionXCliente> cursorToList(Cursor cursor) {
        List<DireccionXCliente> direcciones = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                direcciones.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return direcciones;
    }

    public ArrayList<Direccion> verificarDireccion(int idCliente){
        ArrayList<Direccion> direcciones=new ArrayList<Direccion>();
        //Log.d("AMONGUS", "Sin entrar");
        try {


            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String[] args = new String[]{Integer.toString(idCliente)};
            Cursor c = db.rawQuery("SELECT IdDireccion, Activo FROM " + DireccionXClienteContract.TABLE_NAME +
                    " WHERE IdCliente=? ", args);

            List<Integer> ids = new ArrayList<Integer>();

            /*c.moveToFirst();
            c.moveToNext();
            c.moveToNext();
            Log.d("AMONGBB",c.getString(0));*/
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                Log.d("AMONGUS1",Integer.toString(i));
                Log.d("AMONGUS1",Integer.toString(c.getInt(1)));
                if(c.getString(1).equals("1")){
                    ids.add(Integer.parseInt(c.getString(0)));
                }
                c.moveToNext();
            }
            c.close();

            Direccion direccion;
            for(int i=0;i<ids.size();i++){
                args = new String[]{Integer.toString(ids.get(i))};
                Cursor c2 = db.rawQuery("SELECT Id, Distrito, Canton, Provincia, DireccionExacta FROM "
                        + DireccionContract.TABLE_NAME +
                        " WHERE Id=? ", args);
                c2.moveToFirst();
                direccion= new Direccion();
                direccion.setId(Integer.parseInt(c2.getString(0)));
                direccion.setDistrito(c2.getString(1));
                direccion.setCanton(c2.getString(2));
                direccion.setProvincia(c2.getString(3));
                direccion.setDireccionExacta(c2.getString(4));
                Log.d("AMONGUS2", Integer.toString(i));
                direcciones.add(direccion);
                c2.close();
            }

        }catch (Exception e) {
            e.toString();
        }
        return direcciones;
    }

    @Override
    public int EliminarDireccion(int id) {
        int error = -1;

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(DireccionXClienteContract.COLUMN.ACTIVO, 0);

            db.update(DireccionXClienteContract.TABLE_NAME, values,
                      DireccionXClienteContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

            error= 2;
        }catch (Exception e) {
            e.toString();
            error = -1;
        }
        return error;
    }
}
