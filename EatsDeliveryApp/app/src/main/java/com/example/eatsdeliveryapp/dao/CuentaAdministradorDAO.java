package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.CuentaAdministrador;

//interfaz publica para la cuenta de los admins
public interface CuentaAdministradorDAO extends GenericDAO<CuentaAdministrador, Integer>{

    //metodo que retorna la verificacion del admin
    public int[] verificarAdmin(String idLaboral, String Contrasena );
}
