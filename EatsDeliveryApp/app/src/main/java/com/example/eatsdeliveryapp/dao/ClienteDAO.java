package com.example.eatsdeliveryapp.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.model.Carrito;
import com.example.eatsdeliveryapp.model.Cliente;

//interfaz publica para el cliente dao
public interface ClienteDAO extends GenericDAO<Cliente, Integer> {

    //metodo de save dos
    long save2(Cliente t, Carrito t2, SQLiteDatabase dbConnection);
    Cliente getCliente(int id);
}