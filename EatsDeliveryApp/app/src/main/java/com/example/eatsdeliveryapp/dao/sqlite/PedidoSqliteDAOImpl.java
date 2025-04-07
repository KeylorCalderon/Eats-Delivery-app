package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.sqlite.contracts.PedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoSqliteDAOImpl extends GenericSqliteDAOImpl<Pedido, Integer> implements PedidoDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_PEDIDOS = String.format("select * from %s;", PedidoContract.TABLE_NAME);

    public PedidoSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Pedido> findAll() {
        try {
            String sql = String.format(SQL_SELECT_PEDIDOS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Pedido pedido, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(PedidoContract.COLUMN.ID_ESTADO, pedido.getIdEstado());
        values.put(PedidoContract.COLUMN.ID_DIRECCION, pedido.getDireccion());
        values.put(PedidoContract.COLUMN.ID_CLIENTE, pedido.getIdCliente());
        values.put(PedidoContract.COLUMN.FECHA, pedido.getFecha());
        values.put(PedidoContract.COLUMN.TOTAL_PAGADO, pedido.getTotalPagado());

        return db.insert(PedidoContract.TABLE_NAME, null, values);
    }

    @Override
    protected Pedido cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(PedidoContract.COLUMN.ID));
        String IdEstado = cursor.getString(cursor.getColumnIndex(PedidoContract.COLUMN.ID_ESTADO));
        int IdDireccion=cursor.getInt(cursor.getColumnIndex(PedidoContract.COLUMN.ID_DIRECCION));
        int IdCliente = cursor.getInt(cursor.getColumnIndex(PedidoContract.COLUMN.ID_CLIENTE));
        String Fecha = cursor.getString(cursor.getColumnIndex(PedidoContract.COLUMN.FECHA));
        float TotalPagado = cursor.getFloat(cursor.getColumnIndex(PedidoContract.COLUMN.TOTAL_PAGADO));

        return new Pedido(Id, IdEstado, IdDireccion, IdCliente, Fecha, TotalPagado);
    }

    @Override
    protected List<Pedido> cursorToList(Cursor cursor) {
        List<Pedido> pedidos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                pedidos.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return pedidos;

    }

    @Override
    public List<Pedido> findAllCopy() {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{"En Espera"};
            Cursor c = db.rawQuery("SELECT * FROM " + PedidoContract.TABLE_NAME +
                    " WHERE IdEstado =?", args);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Pedido> findAllActivos(int idCliente) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(idCliente), "En Espera", "Recibido", "En Camino"};
            Cursor c = db.rawQuery("SELECT * FROM " + PedidoContract.TABLE_NAME +
                    " WHERE IdCliente=? AND (IdEstado =? OR IdEstado =? OR IdEstado =?)", args);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Pedido> findAllFinalizados(int idCliente) {
        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(idCliente), "Entregado", "Cancelado"};
            Cursor c = db.rawQuery("SELECT * FROM " + PedidoContract.TABLE_NAME +
                    " WHERE IdCliente=? AND (IdEstado =? OR IdEstado =?)", args);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Pedido getPedido(int id) {

        Pedido pedido= new Pedido();

        try {

            SQLiteDatabase db = this.dbProperties.getReadableConnection();

            String[] args = new String[]{Integer.toString(id)};
            Cursor c = db.rawQuery("SELECT Id, IdEstado, IdDireccion, IdCliente, Fecha, TotalPagado FROM " +
                    PedidoContract.TABLE_NAME + " WHERE Id =?", args);

            c.moveToFirst();

            pedido.setId(c.getInt(0));
            pedido.setIdEstado(c.getString(1));
            pedido.setDireccion(c.getInt(2));
            pedido.setIdCliente(c.getInt(3));
            pedido.setFecha(c.getString(4));
            pedido.setTotalPagado(c.getFloat(5));


        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return pedido;
    }

    @Override
    public void ModEstado(int id, String estado) {

        try {
            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(PedidoContract.COLUMN.ID_ESTADO, estado);
            db.update(PedidoContract.TABLE_NAME, values, PedidoContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

        }catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public void ModTotalPagado(int id, float nuevoTotal) {

        try {
            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            ContentValues values = new ContentValues();
            values.put(PedidoContract.COLUMN.TOTAL_PAGADO, nuevoTotal);
            db.update(PedidoContract.TABLE_NAME, values, PedidoContract.COLUMN.ID + " = ?",
                    new String[]{String.valueOf(id)});

        }catch (Exception e) {
            e.toString();
        }
    }
}
