package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.ItemsCarritoDAO;
import com.example.eatsdeliveryapp.model.ItemsCarrito;

import java.util.List;

public class ItemsCarritoServiceImpl implements ItemsCarritoService{

    private ItemsCarritoDAO DAO;

    public ItemsCarritoServiceImpl(ItemsCarritoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<ItemsCarrito> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(ItemsCarrito c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(ItemsCarrito c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }


}
