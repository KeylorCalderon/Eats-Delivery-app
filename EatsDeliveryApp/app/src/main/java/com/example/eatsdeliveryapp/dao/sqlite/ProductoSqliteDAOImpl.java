package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.ProductoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoSqliteDAOImpl extends GenericSqliteDAOImpl<Producto, Integer> implements ProductoDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_PRODUCTOS = String.format("select * from %s;", ProductoContract.TABLE_NAME);

    public ProductoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Producto> findAll() {
        try {
            String sql = String.format(SQL_SELECT_PRODUCTOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    public Producto findOne(int idProd) {
        Producto producto=new Producto();
        try {
            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            String[] args = new String[]{Integer.toString(idProd)};
            Cursor c = db.rawQuery("SELECT * FROM " + ProductoContract.TABLE_NAME +
                    " WHERE Id=? ", args);
            c.moveToFirst();
            producto.setId(idProd);
            producto.setIdRestaurante(c.getInt(1));
            producto.setNombre(c.getString(2));
            producto.setFoto(c.getBlob(3));
            producto.setDescripcion(c.getString(4));
            producto.setPrecio(c.getFloat(5));
            producto.setCantidad(c.getInt(6));
            Log.d("CANTIDAD", c.getInt(6)+"");
            producto.setActivo(c.getInt(7));

            c.close();
            return producto;

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return producto;
    }

    public List<Producto> findAllPorRestaurante(int idRes) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(idRes),Integer.toString(1)};
            Cursor c = db.rawQuery("SELECT * FROM " + ProductoContract.TABLE_NAME +
                    " WHERE IdRestaurante=? AND Activo=?", args);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Producto producto, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ProductoContract.COLUMN.ID_RESTAURANTE, producto.getIdRestaurante());
        values.put(ProductoContract.COLUMN.NOMBRE, producto.getNombre());

        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        producto.getFoto().compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        values.put(ProductoContract.COLUMN.FOTO, blob);

        values.put(ProductoContract.COLUMN.DESCRIPCION, producto.getDescripcion());
        values.put(ProductoContract.COLUMN.PRECIO, producto.getPrecio());
        values.put(ProductoContract.COLUMN.CANTIDAD, producto.getCantidad());
        values.put(ProductoContract.COLUMN.ACTIVO,producto.getActivo());

        return db.insert(ProductoContract.TABLE_NAME, null, values);
    }

    @Override
    protected Producto cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(ProductoContract.COLUMN.ID));
        int IdRestaurante = cursor.getInt(cursor.getColumnIndex(ProductoContract.COLUMN.ID_RESTAURANTE));
        String Nombre = cursor.getString(cursor.getColumnIndex(ProductoContract.COLUMN.NOMBRE));
        byte[] Foto = cursor.getBlob(cursor.getColumnIndex(ProductoContract.COLUMN.FOTO));
        String Descripcion = cursor.getString(cursor.getColumnIndex(ProductoContract.COLUMN.DESCRIPCION));
        float Precio = cursor.getFloat(cursor.getColumnIndex(ProductoContract.COLUMN.PRECIO));
        int Cantidad = cursor.getInt(cursor.getColumnIndex(ProductoContract.COLUMN.CANTIDAD));
        int Activo = cursor.getInt(cursor.getColumnIndex(ProductoContract.COLUMN.ACTIVO));

        return new Producto(Id, IdRestaurante,Nombre, Foto, Descripcion, Precio, Cantidad,Activo);
    }

    @Override
    protected List<Producto> cursorToList(Cursor cursor) {
        List<Producto> productos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                productos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return productos;

    }

    @Override
    public void EliminarProducto(int id) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(ProductoContract.COLUMN.ACTIVO, 0);
            db.update(ProductoContract.TABLE_NAME, values, ProductoContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

        }catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public int ModificarProducto(Producto producto) {

        int error = -1;

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();

            values.put(ProductoContract.COLUMN.NOMBRE, producto.getNombre());

            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            producto.getFoto().compress(Bitmap.CompressFormat.PNG, 0 , baos);
            byte[] blob = baos.toByteArray();

            values.put(ProductoContract.COLUMN.FOTO, blob);
            values.put(ProductoContract.COLUMN.DESCRIPCION, producto.getDescripcion());
            values.put(ProductoContract.COLUMN.PRECIO, producto.getPrecio());
            values.put(ProductoContract.COLUMN.CANTIDAD, producto.getCantidad());

            db.update(ProductoContract.TABLE_NAME, values, ProductoContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(producto.getId())});

            error = 2;
        }catch (Exception e) {
            e.toString();
        }

        return error;
    }
}
