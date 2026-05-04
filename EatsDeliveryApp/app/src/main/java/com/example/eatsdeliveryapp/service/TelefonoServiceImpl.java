package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.TelefonoDAO;
import com.example.eatsdeliveryapp.model.Telefono;

import java.util.List;

public class TelefonoServiceImpl implements TelefonoService{

    private TelefonoDAO DAO;

    public TelefonoServiceImpl(TelefonoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Telefono> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Telefono t) {
        return this.DAO.save(t, null);
    }

    @Override
    public long addNew(Telefono t, SQLiteDatabase dbConnection) {
        return this.DAO.save(t, dbConnection);
    }

}
