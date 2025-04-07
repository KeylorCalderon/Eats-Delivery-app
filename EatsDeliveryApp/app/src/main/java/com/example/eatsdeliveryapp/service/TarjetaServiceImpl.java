package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.TarjetaDAO;
import com.example.eatsdeliveryapp.model.Tarjeta;

import java.util.List;

public class TarjetaServiceImpl implements TarjetaService{

    private TarjetaDAO DAO;

    public TarjetaServiceImpl(TarjetaDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Tarjeta> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Tarjeta t) {
        return this.DAO.save(t, null);
    }

    @Override
    public long addnew(Tarjeta t, SQLiteDatabase dbConnection) {
        return this.DAO.save(t, dbConnection);
    }


}
