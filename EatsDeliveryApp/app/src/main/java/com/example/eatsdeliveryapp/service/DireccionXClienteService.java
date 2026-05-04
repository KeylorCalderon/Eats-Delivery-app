package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.DireccionXCliente;

import java.util.List;

public interface DireccionXClienteService {
    List<DireccionXCliente> getAll();

    long addNew(DireccionXCliente d);

    long addNew(DireccionXCliente d, SQLiteDatabase dbConnection);
}