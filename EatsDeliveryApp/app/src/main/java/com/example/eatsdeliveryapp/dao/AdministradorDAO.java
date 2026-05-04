package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.Administrador;

//interface dao para el administrador
public interface AdministradorDAO extends GenericDAO<Administrador, Integer>{

    //funcion que retorna un solo admin
    Administrador RetornarAdmin (int idAdmin);
    int validaAdminDefault(int id);
}
