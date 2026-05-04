package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.EstadoPedidoDAO;
import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.model.EstadoPedido;

import java.util.List;

public class EstadoPedidoServiceImpl implements EstadoPedidoService{

    private EstadoPedidoDAO DAO;

    public EstadoPedidoServiceImpl(EstadoPedidoDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<EstadoPedido> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(EstadoPedido c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(EstadoPedido c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }


}
