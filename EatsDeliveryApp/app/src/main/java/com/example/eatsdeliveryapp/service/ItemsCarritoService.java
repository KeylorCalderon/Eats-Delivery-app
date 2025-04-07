package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;
import com.example.eatsdeliveryapp.model.ItemsCarrito;

import java.util.List;

public interface ItemsCarritoService {

    List<ItemsCarrito> getAll();

    long addNew(ItemsCarrito c);

    long addNew(ItemsCarrito c, SQLiteDatabase dbConnection);


}
