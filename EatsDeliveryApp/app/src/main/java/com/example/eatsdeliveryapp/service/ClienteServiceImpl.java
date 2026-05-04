package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.CarritoDAO;
import com.example.eatsdeliveryapp.dao.ClienteDAO;
import com.example.eatsdeliveryapp.model.Carrito;
import com.example.eatsdeliveryapp.model.Cliente;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private ClienteDAO DAO;
    private CarritoDAO DAO2;

    public ClienteServiceImpl(ClienteDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Cliente> getAll() {
        return this.DAO.findAll();
    }



    @Override
    public long addNew(Cliente c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(Cliente c, SQLiteDatabase dbConnection) {
        Date D = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(D);
        Carrito carrito=new Carrito(c.getId(),D.toString(),D.toString());
        return this.DAO.save2(c, carrito, dbConnection);
    }
}