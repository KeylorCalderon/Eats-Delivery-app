package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.Direccion;


////interfaz publica para la direccion dao
public interface DireccionDAO extends GenericDAO<Direccion, Integer>{

    //funcion para actualizar dirreciones
    int ActualizarDireccion(Direccion direccion);
    Direccion getDireccion(int id);
}
