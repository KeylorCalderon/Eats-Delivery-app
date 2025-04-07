package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.SolicitudEliminacion;

import java.util.List;

public interface SolicitudEliminacionService {

    List<SolicitudEliminacion> getAll();

    long addNew (SolicitudEliminacion s);

    long addnew (SolicitudEliminacion s, SQLiteDatabase dbConnection);

}
