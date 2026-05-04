package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.ItemsPedido;

import java.util.List;

public interface ItemsPedidoService {

    List<ItemsPedido> getAll();

    long addNew(ItemsPedido c);

    long addNew(ItemsPedido c, SQLiteDatabase dbConnection);


}
