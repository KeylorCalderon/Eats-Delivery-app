package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Direccion;

import java.util.List;

public interface DireccionService {
    List<Direccion> getAll();

    long addNew(Direccion d);

    long addNew(Direccion d, SQLiteDatabase dbConnection);
}
