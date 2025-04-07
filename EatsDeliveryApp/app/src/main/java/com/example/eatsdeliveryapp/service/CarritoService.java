package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Carrito;

import java.util.List;

public interface CarritoService {

    List<Carrito> getAll();

    long addNew(Carrito c);

    long addNew(Carrito c, SQLiteDatabase dbConnection);

}
