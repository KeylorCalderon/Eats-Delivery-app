package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.ProductoDAO;
import com.example.eatsdeliveryapp.model.Producto;

import java.util.List;

public class ProductoServiceImpl implements ProductoService{

    private ProductoDAO DAO;

    public ProductoServiceImpl(ProductoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Producto> getAll() {
        return this.DAO.findAll();
    }


    @Override
    public long addNew(Producto p) {
        return this.DAO.save(p, null);
    }

    @Override
    public long addNew(Producto p, SQLiteDatabase dbConnection) {
        return this.DAO.save(p, dbConnection);
    }


}
