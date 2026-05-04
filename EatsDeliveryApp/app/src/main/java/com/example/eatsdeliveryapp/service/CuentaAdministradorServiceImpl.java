package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.CuentaAdministradorDAO;
import com.example.eatsdeliveryapp.model.CuentaAdministrador;


import java.util.List;

public class CuentaAdministradorServiceImpl implements CuentaAdministradorService{

    private CuentaAdministradorDAO DAO;

    public CuentaAdministradorServiceImpl(CuentaAdministradorDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<CuentaAdministrador> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(CuentaAdministrador c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(CuentaAdministrador c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }
}
