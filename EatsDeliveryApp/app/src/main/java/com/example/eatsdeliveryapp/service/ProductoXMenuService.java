package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.ProductoXMenu;

import java.util.List;


public interface ProductoXMenuService {

    List<ProductoXMenu> getAll();

    long addNew (ProductoXMenu p);

    long addnew (ProductoXMenu p, SQLiteDatabase dbConnection);
}
