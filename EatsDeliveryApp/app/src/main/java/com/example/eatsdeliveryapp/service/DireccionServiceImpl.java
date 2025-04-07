package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.model.Direccion;

import java.util.List;

public class DireccionServiceImpl implements  DireccionService{
    private DireccionDAO DAO;

    public DireccionServiceImpl(DireccionDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Direccion> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Direccion d) {
        return this.DAO.save(d, null);
    }

    @Override
    public long addNew(Direccion d, SQLiteDatabase dbConnection) {
        return this.DAO.save(d, dbConnection);
    }
}
