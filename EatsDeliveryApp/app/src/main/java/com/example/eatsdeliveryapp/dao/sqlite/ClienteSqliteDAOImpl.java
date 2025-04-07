package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.ClienteDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Carrito;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.sqlite.contracts.CarritoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteSqliteDAOImpl extends GenericSqliteDAOImpl<Cliente, Integer> implements ClienteDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_CLIENTES = String.format("select * from %s;", ClienteContract.TABLE_NAME);

    public ClienteSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Cliente> findAll() {
        try {
            String sql = String.format(SQL_SELECT_CLIENTES);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Cliente cliente, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ClienteContract.COLUMN.NOMBRE_USUARIO, cliente.getNombreUsuario());
        values.put(ClienteContract.COLUMN.NOMBRE, cliente.getNombre());
        values.put(ClienteContract.COLUMN.PRIMER_APELLIDO, cliente.getPrimerApellido());
        values.put(ClienteContract.COLUMN.SEGUNDO_APELLIDO, cliente.getSegundoApellido());
        values.put(ClienteContract.COLUMN.CORREO, cliente.getCorreo());

        return db.insert(ClienteContract.TABLE_NAME, null, values);
    }

    @Override
    public long save2(Cliente cliente, Carrito carrito, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(ClienteContract.COLUMN.NOMBRE_USUARIO, cliente.getNombreUsuario());
        values.put(ClienteContract.COLUMN.NOMBRE, cliente.getNombre());
        values.put(ClienteContract.COLUMN.PRIMER_APELLIDO, cliente.getPrimerApellido());
        values.put(ClienteContract.COLUMN.SEGUNDO_APELLIDO, cliente.getSegundoApellido());
        values.put(ClienteContract.COLUMN.CORREO, cliente.getCorreo());

        long retornar=db.insert(ClienteContract.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(CarritoContract.COLUMN.ID_CLIENTE, retornar);
        values.put(CarritoContract.COLUMN.FECHA_CREACION, carrito.getFechaCreacion());
        values.put(CarritoContract.COLUMN.FECHA_MODIFICACION, carrito.getFechaModificacion());

        db.insert(CarritoContract.TABLE_NAME, null, values);
        return retornar;
    }


    @Override
    protected Cliente cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(ClienteContract.COLUMN.ID));
        String NombreUsuario = cursor.getString(cursor.getColumnIndex(ClienteContract.COLUMN.NOMBRE_USUARIO));
        String Nombre = cursor.getString(cursor.getColumnIndex(ClienteContract.COLUMN.NOMBRE));
        String PrimerApellido = cursor.getString(cursor.getColumnIndex(ClienteContract.COLUMN.PRIMER_APELLIDO));
        String SegundoApellido = cursor.getString(cursor.getColumnIndex(ClienteContract.COLUMN.SEGUNDO_APELLIDO));
        String Correo = cursor.getString(cursor.getColumnIndex(ClienteContract.COLUMN.CORREO));
        return new Cliente(Id, NombreUsuario, Nombre, PrimerApellido, SegundoApellido, Correo);
    }

    @Override
    protected List<Cliente> cursorToList(Cursor cursor) {
        List<Cliente> clientes = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                clientes.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return clientes;
    }


    @Override
    public Cliente getCliente(int id) {

        Cliente cliente= new Cliente();
        try {
        SQLiteDatabase db = this.dbProperties.getReadableConnection();

        String[] args = new String[]{Integer.toString(id)};
        Cursor c = db.rawQuery("SELECT Id, Nombre, PrimerApellido, SegundoApellido, Correo FROM " +
                ClienteContract.TABLE_NAME +
                " WHERE Id=? ", args);
        c.moveToFirst();

        cliente.setId(c.getInt(0));
        cliente.setNombre(c.getString(1));
        cliente.setPrimerApellido(c.getString(2));
        cliente.setSegundoApellido(c.getString(3));
        cliente.setCorreo(c.getString(4));

        }catch (Exception e) {
            e.toString();
        }

        return cliente;
    }
}