package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Puesto;

import java.util.List;

public interface PuestoService {

    List<Puesto> getAll();

    long addNew (Puesto p);

    long addnew (Puesto p, SQLiteDatabase dbConnection);

}
