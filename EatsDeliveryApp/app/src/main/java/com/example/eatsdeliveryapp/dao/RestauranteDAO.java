package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.ArrayList;

//interfaz publica para el restaurante dao
public interface RestauranteDAO  extends GenericDAO<Restaurante, Integer>{

    //funciones implementados en la clase
    ArrayList<String> consultaRestauranteGerente();
    void EliminarRestaurante(int id);
    String infoRestauranteUnico(int id);
    int ActualizarRestaurante(Restaurante restaurante);
}
