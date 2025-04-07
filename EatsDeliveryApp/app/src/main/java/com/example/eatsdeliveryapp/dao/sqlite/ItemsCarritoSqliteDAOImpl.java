package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.ItemsCarritoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.ItemsCarrito;
import com.example.eatsdeliveryapp.sqlite.contracts.ItemsCarritoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ItemsPedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//Clase publica para las operaciones sobre la tabla de items carrito
public class ItemsCarritoSqliteDAOImpl extends GenericSqliteDAOImpl<ItemsCarrito, Integer> implements ItemsCarritoDAO {

    //propiedades de la base
    private final DBProperties dbProperties;

    //string final de consulta
    private static final String ITEMS_CARRITO = String.format("select * from %s where Activo = 1;", ItemsCarritoContract.TABLE_NAME);

    //constructot
    public ItemsCarritoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<ItemsCarrito> findAll() {
        try {
            String sql = String.format(ITEMS_CARRITO);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    public void EliminarProducto(int idProducto, int idCarrito) {
        try {

            SQLiteDatabase db = this.dbProperties.getWritableConnection();
            //ContentValues values = new ContentValues();
            int band;
            band=db.delete(ItemsCarritoContract.TABLE_NAME,ItemsCarritoContract.COLUMN.ID_PRODUCTO + "=? AND "+
                            ItemsCarritoContract.COLUMN.ID_CARRITO + " =? ",
                          new String[]{Integer.toString(idProducto), Integer.toString(idCarrito)});
            Log.d("ABC","El resultado fue: "+band);

        }catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public long save(ItemsCarrito items_carrito, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ItemsCarritoContract.COLUMN.ID_PRODUCTO, items_carrito.getIdProducto());
        values.put(ItemsCarritoContract.COLUMN.ID_CARRITO, items_carrito.getIdCarrito());
        values.put(ItemsCarritoContract.COLUMN.CANTIDAD, items_carrito.getCantidad());
        values.put(ItemsCarritoContract.COLUMN.ACTIVO, items_carrito.getActivo());

        return db.insert(ItemsCarritoContract.TABLE_NAME, null, values);
    }

    @Override
    protected ItemsCarrito cursorToEntity(Cursor cursor) {
        int IdProducto = cursor.getInt(cursor.getColumnIndex(ItemsCarritoContract.COLUMN.ID_PRODUCTO));
        int IdPedido = cursor.getInt(cursor.getColumnIndex(ItemsCarritoContract.COLUMN.ID_CARRITO));
        int Cantidad = cursor.getInt(cursor.getColumnIndex(ItemsCarritoContract.COLUMN.CANTIDAD));
        int Activo = cursor.getInt(cursor.getColumnIndex(ItemsCarritoContract.COLUMN.ACTIVO));


        return new ItemsCarrito(IdProducto, IdPedido, Cantidad, Activo);
    }

    @Override
    protected List<ItemsCarrito> cursorToList(Cursor cursor) {
        List<ItemsCarrito> items_carrito = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                items_carrito.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return items_carrito;

    }

    @Override
    public void ModificarItem(int idProducto, int idCarrito, int cantidad) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(ItemsCarritoContract.COLUMN.CANTIDAD, cantidad);
            db.update(ItemsCarritoContract.TABLE_NAME,values, ItemsCarritoContract.COLUMN.ID_PRODUCTO + "=? AND "+
                            ItemsCarritoContract.COLUMN.ID_CARRITO + " =? ",
                    new String[]{Integer.toString(idProducto), Integer.toString(idCarrito)});

        }catch (Exception e) {
            e.toString();
        }
    }
}
