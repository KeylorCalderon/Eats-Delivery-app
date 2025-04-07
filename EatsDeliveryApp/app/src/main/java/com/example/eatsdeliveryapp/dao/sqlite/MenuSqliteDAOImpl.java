package com.example.eatsdeliveryapp.dao.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.MenuDAO;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Menu;
import com.example.eatsdeliveryapp.sqlite.contracts.MenuContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuSqliteDAOImpl extends GenericSqliteDAOImpl<Menu, Integer> implements MenuDAO {

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_MENUS = String.format("select * from %s;", MenuContract.TABLE_NAME);

    public MenuSqliteDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Menu> findAll() {
        try {
            String sql = String.format(SQL_SELECT_MENUS);

            SQLiteDatabase db = this.dbProperties.getReadableConnection();
            Cursor c = db.rawQuery(sql, null);

            return this.cursorToList(c);

        } catch (Exception e) {
            Log.d("MYAPP", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public long save(Menu pedido, SQLiteDatabase dbConnection) {

        SQLiteDatabase db = dbConnection != null ? dbConnection : this.dbProperties.getWritableConnection();

        ContentValues values = new ContentValues();
        values.put(MenuContract.COLUMN.ID_RESTAURANTE, pedido.getIdRestaurante());
        return db.insert(MenuContract.TABLE_NAME, null, values);
    }

    @Override
    protected Menu cursorToEntity(Cursor cursor) {
        int Id = cursor.getInt(cursor.getColumnIndex(MenuContract.COLUMN.ID));
        int IdRestaurante = cursor.getInt(cursor.getColumnIndex(MenuContract.COLUMN.ID_RESTAURANTE));


        return new Menu(Id, IdRestaurante);
    }

    @Override
    protected List<Menu> cursorToList(Cursor cursor) {
        List<Menu> menus = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                menus.add(this.cursorToEntity(cursor));
            } while (cursor.moveToNext());
        }

        return menus;

    }









}
