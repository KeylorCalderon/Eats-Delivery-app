package com.example.eatsdeliveryapp.dao;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.List;

////interfaz publica de generic dao, a partir de el se hacen los demas
public interface GenericDAO<T, ID extends Serializable> {

    List<T> findAll();

    long save(T t, SQLiteDatabase dbConnection);

}