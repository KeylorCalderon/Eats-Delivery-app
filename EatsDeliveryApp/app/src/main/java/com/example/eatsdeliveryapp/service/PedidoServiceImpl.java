package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.ClienteDAO;
import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.model.Pedido;

import java.util.List;

public class PedidoServiceImpl implements PedidoService{

    private PedidoDAO DAO;

    public PedidoServiceImpl(PedidoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Pedido> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Pedido c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(Pedido c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }


}
