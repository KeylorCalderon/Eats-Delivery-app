package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;


import com.example.eatsdeliveryapp.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    long addNew(Menu c);

    long addNew(Menu c, SQLiteDatabase dbConnection);
}
