package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;


import com.example.eatsdeliveryapp.dao.CarritoDAO;
import com.example.eatsdeliveryapp.model.Carrito;

import java.util.List;

public class CarritoServiceImpl implements CarritoService{

    private CarritoDAO DAO;

    public CarritoServiceImpl(CarritoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Carrito> getAll() {
        return this.DAO.findAll();
    }



    @Override
    public long addNew(Carrito c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(Carrito c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }


}
