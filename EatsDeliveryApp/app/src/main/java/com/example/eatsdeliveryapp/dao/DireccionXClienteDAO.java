package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.DireccionXCliente;

//interfaz publica para las direccion x cliente dao
public interface DireccionXClienteDAO extends GenericDAO<DireccionXCliente, Integer>{

    int EliminarDireccion(int id);
}
