package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.DireccionXClienteDAO;
import com.example.eatsdeliveryapp.model.DireccionXCliente;

import java.util.List;

public class DireccionXClienteServiceImpl implements  DireccionXClienteService{
    private DireccionXClienteDAO DAO;

    public DireccionXClienteServiceImpl(DireccionXClienteDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<DireccionXCliente> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(DireccionXCliente d) {
        return this.DAO.save(d, null);
    }

    @Override
    public long addNew(DireccionXCliente d, SQLiteDatabase dbConnection) {
        return this.DAO.save(d, dbConnection);
    }
}
