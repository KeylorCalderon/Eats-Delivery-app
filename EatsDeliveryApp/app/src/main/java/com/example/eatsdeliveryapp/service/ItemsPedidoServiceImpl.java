package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;


import com.example.eatsdeliveryapp.dao.ItemsPedidoDAO;
import com.example.eatsdeliveryapp.model.ItemsPedido;

import java.util.List;

public class ItemsPedidoServiceImpl implements ItemsPedidoService{

    private ItemsPedidoDAO DAO;

    public ItemsPedidoServiceImpl(ItemsPedidoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<ItemsPedido> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(ItemsPedido c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(ItemsPedido c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }

}
