package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.ItemsPedidoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.ItemsPedido;
import com.example.eatsdeliveryapp.sqlite.contracts.ItemsPedidoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsPedidoSqliteDAOImpl extends GenericSqliteDAOImpl<ItemsPedido, Integer> implements ItemsPedidoDAO {

    private final DBProperties dbProperties;

    private static final String ITEMS_PEDIDO = String.format("select * from %s;", ItemsPedidoContract.TABLE_NAME);

    public ItemsPedidoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<ItemsPedido> findAll() {
        try {
            String sql = String.format(ITEMS_PEDIDO);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(ItemsPedido items_pedido, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ItemsPedidoContract.COLUMN.ID_PRODUCTO, items_pedido.getIdProducto());
        values.put(ItemsPedidoContract.COLUMN.ID_PEDIDO, items_pedido.getIdPedido());
        values.put(ItemsPedidoContract.COLUMN.CANTIDAD, items_pedido.getCantidad());
        values.put(ItemsPedidoContract.COLUMN.ACTIVO, items_pedido.getActivo());

        return db.insert(ItemsPedidoContract.TABLE_NAME, null, values);
    }

    @Override
    protected ItemsPedido cursorToEntity(Cursor cursor) {
        int IdProducto = cursor.getInt(cursor.getColumnIndex(ItemsPedidoContract.COLUMN.ID_PRODUCTO));
        int IdPedido = cursor.getInt(cursor.getColumnIndex(ItemsPedidoContract.COLUMN.ID_PEDIDO));
        int Cantidad = cursor.getInt(cursor.getColumnIndex(ItemsPedidoContract.COLUMN.CANTIDAD));
        int Activo = cursor.getInt(cursor.getColumnIndex(ItemsPedidoContract.COLUMN.ACTIVO));


        return new ItemsPedido(IdProducto, IdPedido, Cantidad, Activo);
    }

    @Override
    protected List<ItemsPedido> cursorToList(Cursor cursor) {
        List<ItemsPedido> pedidos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                pedidos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return pedidos;

    }


}
