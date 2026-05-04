package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.ProductoXMenuDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.ProductoXMenu;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoXMenuContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoXMenuSqliteDAOImpl extends GenericSqliteDAOImpl<ProductoXMenu, Integer> implements ProductoXMenuDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_PRODUCTOS_POR_PEDIDO = String.format("select * from %s;", ProductoXMenuContract.TABLE_NAME);

    public ProductoXMenuSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<ProductoXMenu> findAll() {
        try {
            String sql = String.format(SQL_SELECT_PRODUCTOS_POR_PEDIDO);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(ProductoXMenu productoxmenu, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ProductoXMenuContract.COLUMN.ID_PRODUCTO, productoxmenu.getIdProducto());
        values.put(ProductoXMenuContract.COLUMN.ID_MENU, productoxmenu.getIdMenu());
        values.put(ProductoXMenuContract.COLUMN.CANTIDAD_STOCK, productoxmenu.getCantidadStock());
        return db.insert(ProductoXMenuContract.TABLE_NAME, null, values);
    }

    @Override
    protected ProductoXMenu cursorToEntity(Cursor cursor) {
        int IdProducto = cursor.getInt(cursor.getColumnIndex(ProductoXMenuContract.COLUMN.ID_PRODUCTO));
        int IdMenu = cursor.getInt(cursor.getColumnIndex(ProductoXMenuContract.COLUMN.ID_MENU));
        int CantidadStock = cursor.getInt(cursor.getColumnIndex(ProductoXMenuContract.COLUMN.CANTIDAD_STOCK));
        return new ProductoXMenu(IdProducto, IdMenu, CantidadStock);
    }

    @Override
    protected List<ProductoXMenu> cursorToList(Cursor cursor) {
        List<ProductoXMenu> productosxmenus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                productosxmenus.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return productosxmenus;

    }


}
