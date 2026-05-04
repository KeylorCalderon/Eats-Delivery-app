package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Administrador;

import java.util.List;

public interface AdministradorService {

    List<Administrador> getAll();

    long addNew(Administrador a);

    long addNew(Administrador a, SQLiteDatabase dbConnection);

}
