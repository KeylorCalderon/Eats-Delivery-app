package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.PuestoDAO;
import com.example.eatsdeliveryapp.model.Puesto;

import java.util.List;

public class PuestoServiceImpl implements PuestoService{

    private PuestoDAO DAO;

    public PuestoServiceImpl(PuestoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Puesto> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Puesto p) {
        return this.DAO.save(p, null);
    }

    @Override
    public long addnew(Puesto p, SQLiteDatabase dbConnection) {
        return this.DAO.save(p, dbConnection);
    }
}
