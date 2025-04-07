package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Tarjeta;

import java.util.List;

public interface TarjetaService {

    List<Tarjeta> getAll();

    long addNew (Tarjeta t);

    long addnew (Tarjeta t, SQLiteDatabase dbConnection);
}
