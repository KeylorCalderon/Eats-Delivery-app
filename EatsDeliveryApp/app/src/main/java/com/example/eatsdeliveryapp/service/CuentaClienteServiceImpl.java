package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.CuentaClienteDAO;
import com.example.eatsdeliveryapp.model.CuentaCliente;

import java.util.List;

public class CuentaClienteServiceImpl implements CuentaClienteService {

    private CuentaClienteDAO DAO;

    public CuentaClienteServiceImpl(CuentaClienteDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<CuentaCliente> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(CuentaCliente c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(CuentaCliente c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }
}