package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.EstadoPedido;

import java.util.List;

public interface EstadoPedidoService {

    List<EstadoPedido> getAll();

    long addNew(EstadoPedido c);

    long addNew(EstadoPedido c, SQLiteDatabase dbConnection);


}
