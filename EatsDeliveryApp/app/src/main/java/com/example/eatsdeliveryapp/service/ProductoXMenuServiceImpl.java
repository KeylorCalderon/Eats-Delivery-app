package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;


import com.example.eatsdeliveryapp.dao.ProductoXMenuDAO;
import com.example.eatsdeliveryapp.model.ProductoXMenu;


import java.util.List;

public class ProductoXMenuServiceImpl implements ProductoXMenuService {

    private ProductoXMenuDAO DAO;

    public ProductoXMenuServiceImpl(ProductoXMenuDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<ProductoXMenu> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(ProductoXMenu p) {
        return this.DAO.save(p, null);
    }

    @Override
    public long addnew(ProductoXMenu p, SQLiteDatabase dbConnection) {
        return this.DAO.save(p, dbConnection);
    }



}
