package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.CuentaCliente;

import java.util.List;

public interface CuentaClienteService {

    List<CuentaCliente> getAll();

    long addNew(CuentaCliente c);

    long addNew(CuentaCliente c, SQLiteDatabase dbConnection);

}