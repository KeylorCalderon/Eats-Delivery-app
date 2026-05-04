package com.example.eatsdeliveryapp.dao.sqlite;

import android.database.Cursor;

import com.example.eatsdeliveryapp.dao.GenericDAO;

import java.io.Serializable;
import java.util.List;

public abstract class GenericSqliteDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected abstract T cursorToEntity(Cursor cursor);

    protected abstract List<T> cursorToList(Cursor cursor);

}