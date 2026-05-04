package com.example.eatsdeliveryapp.service;

import android.database.sqlite.SQLiteDatabase;

import com.example.eatsdeliveryapp.dao.MenuDAO;
import com.example.eatsdeliveryapp.model.Menu;

import java.util.List;

public class MenuServiceImpl implements MenuService{

    private MenuDAO DAO;

    public MenuServiceImpl(MenuDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public List<Menu> getAll() {
        return this.DAO.findAll();
    }

    @Override
    public long addNew(Menu c) {
        return this.DAO.save(c, null);
    }

    @Override
    public long addNew(Menu c, SQLiteDatabase dbConnection) {
        return this.DAO.save(c, dbConnection);
    }


}
