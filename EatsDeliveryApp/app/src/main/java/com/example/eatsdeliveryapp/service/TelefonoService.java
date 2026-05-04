package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Telefono;

import java.util.List;

public interface TelefonoService {

    List<Telefono> getAll();

    long addNew (Telefono t);
    long addNew (Telefono t, SQLiteDatabase dbConnection);
}
