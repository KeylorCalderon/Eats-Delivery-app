package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.CuentaAdministrador;

import java.util.List;

public interface CuentaAdministradorService {

    List<CuentaAdministrador> getAll();

    long addNew(CuentaAdministrador c);

    long addNew(CuentaAdministrador c, SQLiteDatabase dbConnection);

}
