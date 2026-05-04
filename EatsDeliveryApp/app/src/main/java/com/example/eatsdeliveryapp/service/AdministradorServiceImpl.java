package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.AdministradorDAO;
import com.example.eatsdeliveryapp.model.Administrador;

import java.util.List;


public class AdministradorServiceImpl implements AdministradorService{

    private AdministradorDAO DAO;

    public AdministradorServiceImpl(AdministradorDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Administrador> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Administrador a) {
        return this.DAO.save(a, null);
    }

    @Override
    public long addNew(Administrador a, SQLiteDatabase dbConnection) {
        return this.DAO.save(a, dbConnection);
    }
}
