package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;
import com.example.eatsdeliveryapp.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> getAll();

    long addNew(Producto p);

    long addNew(Producto p, SQLiteDatabase dbConnection);

}
