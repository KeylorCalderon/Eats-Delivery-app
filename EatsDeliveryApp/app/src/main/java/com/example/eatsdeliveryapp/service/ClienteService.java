package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> getAll();

    long addNew(Cliente c);

    long addNew(Cliente c, SQLiteDatabase dbConnection);

}