package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.SolicitudEliminacionDAO;
import com.example.eatsdeliveryapp.model.SolicitudEliminacion;

import java.util.List;

public class SolicitudEliminacionServiceImpl implements SolicitudEliminacionService{

    private SolicitudEliminacionDAO DAO;

    public SolicitudEliminacionServiceImpl(SolicitudEliminacionDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<SolicitudEliminacion> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(SolicitudEliminacion s) {
        return this.DAO.save(s, null);
    }

    @Override
    public long addnew(SolicitudEliminacion s, SQLiteDatabase dbConnection) {
        return this.DAO.save(s, dbConnection);
    }
}
