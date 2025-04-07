package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;
import com.example.eatsdeliveryapp.model.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> getAll();

    long addNew(Pedido c);

    long addNew(Pedido c, SQLiteDatabase dbConnection);

}
