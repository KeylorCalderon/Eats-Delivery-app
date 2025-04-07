package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.RestauranteDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestauranteSqliteDAOImpl  extends GenericSqliteDAOImpl<Restaurante, Integer> implements RestauranteDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_RESTAURANTES = String.format("select * from %s where Activo = 1;", RestauranteContract.TABLE_NAME);

    public RestauranteSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Restaurante> findAll() {
        try {
            String sql = String.format(SQL_SELECT_RESTAURANTES);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    public Restaurante recuperarRestaurante(int idRestaurante){
       Restaurante restaurante=new Restaurante();
        //Log.d("AMONGUS", "Sin entrar");
        try {


            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(idRestaurante)};
            Cursor c = db.rawQuery("SELECT IdDireccion, Nombre, Categoria, Foto, Activo FROM " +
                    RestauranteContract.TABLE_NAME +
                    " WHERE Id=? ", args);



            c.moveToFirst();
            restaurante.setId(idRestaurante);

            restaurante.setIdDireccion(c.getInt(0));
            restaurante.setNombre(c.getString(1));
            restaurante.setCategoria(c.getString(2));
            restaurante.setFoto(c.getBlob(3));
            restaurante.setActivo(c.getInt(4));


        }catch (Exception e) {
            e.toString();
        }
        return restaurante;
    }

    @Override
    public long save(Restaurante restaurante, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(RestauranteContract.COLUMN.ID_DIRECCION, restaurante.getIdDireccion());
        values.put(RestauranteContract.COLUMN.NOMBRE, restaurante.getNombre());
        values.put(RestauranteContract.COLUMN.CATEGORIA, restaurante.getCategoria());

        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        restaurante.getFoto().compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        values.put(RestauranteContract.COLUMN.FOTO, blob);
        values.put(RestauranteContract.COLUMN.ACTIVO, restaurante.getActivo());

        return db.insert(RestauranteContract.TABLE_NAME, null, values);
    }

    @Override
    protected Restaurante cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(RestauranteContract.COLUMN.ID));
        int IdDireccion = cursor.getInt(cursor.getColumnIndex(RestauranteContract.COLUMN.ID_DIRECCION));
        String Nombre = cursor.getString(cursor.getColumnIndex(RestauranteContract.COLUMN.NOMBRE));
        String Categoria = cursor.getString(cursor.getColumnIndex(RestauranteContract.COLUMN.CATEGORIA));
        byte[] Foto = cursor.getBlob(cursor.getColumnIndex(RestauranteContract.COLUMN.FOTO));
        int Activo = cursor.getInt(cursor.getColumnIndex(RestauranteContract.COLUMN.ACTIVO));

        return new Restaurante(Id, IdDireccion, Nombre, Categoria, Foto, Activo);
    }

    @Override
    protected List<Restaurante> cursorToList(Cursor cursor) {
        List<Restaurante> restaurantes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                restaurantes.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return restaurantes;

    }

    @Override
    public ArrayList<String> consultaRestauranteGerente() {

        ArrayList<String> info = new ArrayList<>();

        List<Restaurante> restaurantes = findAll();

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String args[];

            for(int i=0; i<restaurantes.size(); i++){

                args = new String[]{Integer.toString(restaurantes.get(i).getIdDireccion())};

                Cursor c2 = db.rawQuery("SELECT Id, Distrito, Canton, Provincia, DireccionExacta FROM "
                        + DireccionContract.TABLE_NAME +
                        " WHERE Id=? ", args);
                c2.moveToFirst();


                String datos = "Nombre:" + restaurantes.get(i).getNombre()+"\n"+
                               "Categoría: "+restaurantes.get(i).getCategoria()+"\n"+
                               "Dirección: "+c2.getString(3)+", "+c2.getString(2)+", "+
                                c2.getString(1)+", "+c2.getString(4);

                info.add(datos);
                c2.close();
            }

        }catch (Exception e) {
            e.toString();
        }
        return info;
    }

    @Override
    public void EliminarRestaurante(int id) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(RestauranteContract.COLUMN.ACTIVO, 0);
            db.update(RestauranteContract.TABLE_NAME, values, RestauranteContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

        }catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public String infoRestauranteUnico(int id) {

        String info="";
        Restaurante restaurante = recuperarRestaurante(id);

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String args[];

            args = new String[]{Integer.toString(restaurante.getIdDireccion())};

            Cursor c = db.rawQuery("SELECT Id, Distrito, Canton, Provincia, DireccionExacta FROM "
                        + DireccionContract.TABLE_NAME +
                        " WHERE Id=? ", args);
            c.moveToFirst();


            info = "Nombre: " + restaurante.getNombre()+"\n"+
                        "Categoría: "+restaurante.getCategoria()+"\n"+
                        "Dirección: "+c.getString(3)+", "+c.getString(2)+", "+
                        c.getString(1)+", "+c.getString(4);
            c.close();

        }catch (Exception e) {
            e.toString();
        }
        return info;
    }

    @Override
    public int ActualizarRestaurante(Restaurante restaurante) {
        int error=-1;

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();

            values.put(RestauranteContract.COLUMN.NOMBRE, restaurante.getNombre());
            values.put(RestauranteContract.COLUMN.CATEGORIA, restaurante.getCategoria());

            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            restaurante.getFoto().compress(Bitmap.CompressFormat.PNG, 0 , baos);
            byte[] blob = baos.toByteArray();

            values.put(RestauranteContract.COLUMN.FOTO, blob);

            db.update(RestauranteContract.TABLE_NAME, values, RestauranteContract.COLUMN.ID+" = ?",
                    new String[]{String.valueOf(restaurante.getId())});
            error = 2;

        }catch (Exception e) {
            e.toString();
        }
        return error;

    }
}
