package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.EstadoPedidoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.EstadoPedido;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.sqlite.contracts.EstadoPedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.PedidoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Clase publica para las operaciones sobre la tabla estado pedido
public class EstadoPedidoSqliteDAOImpl extends GenericSqliteDAOImpl<EstadoPedido, Integer> implements EstadoPedidoDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_ESTADO_PEDIDOS = String.format("select * from %s;", EstadoPedidoContract.TABLE_NAME);

    public EstadoPedidoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<EstadoPedido> findAll() {
        try {
            String sql = String.format(SQL_SELECT_ESTADO_PEDIDOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(EstadoPedido pedido, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(EstadoPedidoContract.COLUMN.ID, pedido.getId());
        values.put(EstadoPedidoContract.COLUMN.NOMBRE, pedido.getNombre());

        return db.insert(PedidoContract.TABLE_NAME, null, values);
    }

    @Override
    protected EstadoPedido cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(EstadoPedidoContract.COLUMN.ID));
        String Nombre = cursor.getString(cursor.getColumnIndex(EstadoPedidoContract.COLUMN.NOMBRE));

        return new EstadoPedido(Id, Nombre);
    }

    @Override
    protected List<EstadoPedido> cursorToList(Cursor cursor) {
        List<EstadoPedido> estados_pedidos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                estados_pedidos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return estados_pedidos;

    }







}
