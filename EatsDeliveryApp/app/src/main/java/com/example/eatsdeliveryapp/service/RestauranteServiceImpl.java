package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.RestauranteDAO;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.List;

public class RestauranteServiceImpl  implements RestauranteService {

    private RestauranteDAO DAO;

    public RestauranteServiceImpl(RestauranteDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Restaurante> getAll() {
        return this.DAO.findAll();
    }


    @Override
    public long addNew(Restaurante R) {
        return this.DAO.save(R, null);
    }

    @Override
    public long addNew(Restaurante R, SQLiteDatabase dbConnection) {
        return this.DAO.save(R, dbConnection);
    }
}
