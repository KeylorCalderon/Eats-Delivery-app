package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.CuentaCliente;

//interfaz publica para la cuenta cliente
public interface CuentaClienteDAO extends GenericDAO<CuentaCliente, Integer> {

    //funcion que verifica los clientes
    public int verificarCliente(String NombreUsuario, String Contrasena );

}
