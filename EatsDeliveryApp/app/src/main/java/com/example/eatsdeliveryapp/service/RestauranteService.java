package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.List;

public interface RestauranteService {
    List<Restaurante> getAll();

    long addNew(Restaurante R);

    long addNew(Restaurante R, SQLiteDatabase dbConnection);
}
