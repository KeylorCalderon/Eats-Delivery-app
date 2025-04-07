package com.example.eatsdeliveryapp.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.TarjetaDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Tarjeta;
import com.example.eatsdeliveryapp.sqlite.contracts.TarjetaContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TarjetaSqliteDAOImpl extends GenericSqliteDAOImpl<Tarjeta, Integer> implements TarjetaDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_TARJETAS = String.format("select * from %s;", TarjetaContract.TABLE_NAME);

    public TarjetaSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Tarjeta> findAll() {
        try {
            String sql = String.format(SQL_SELECT_TARJETAS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Tarjeta tarjeta, SQLiteDatabase dbConnection) {
        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        //Preparamos los datos
        ContentValues values = new ContentValues();
        values.put("IdCliente", tarjeta.getIdCliente());
        values.put("Numero", tarjeta.getNumero());
        values.put("NombreTitular", tarjeta.getNombreTitural());
        values.put("FechaVencimiento", tarjeta.getFechaVencimiento());
        values.put("CodigoSeguridad", tarjeta.getCodigoSeguridad());

        return db.insert(TarjetaContract.TABLE_NAME, null, values);
    }

    @Override
    protected Tarjeta cursorToEntity(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(TarjetaContract.COLUMN.ID));
        int idCliente = cursor.getInt(cursor.getColumnIndex(TarjetaContract.COLUMN.ID_CLIENTE));
        int numeroTarjeta = cursor.getInt(cursor.getColumnIndex(TarjetaContract.COLUMN.NUMERO));
        String nombreTitular = cursor.getString(cursor.getColumnIndex(TarjetaContract.COLUMN.NOMBRE_TITULAR));
        String vence = cursor.getString(cursor.getColumnIndex(TarjetaContract.COLUMN.FECHA_VENCIMIENTO));
        int csv = cursor.getInt(cursor.getColumnIndex(TarjetaContract.COLUMN.CODIGO_SEGURIDAD));

        return new Tarjeta(id,idCliente, numeroTarjeta, nombreTitular, vence, csv);
    }

    @Override
    protected List<Tarjeta> cursorToList(Cursor cursor) {
        List<Tarjeta> tarjetas = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                tarjetas.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return tarjetas;
    }
}
