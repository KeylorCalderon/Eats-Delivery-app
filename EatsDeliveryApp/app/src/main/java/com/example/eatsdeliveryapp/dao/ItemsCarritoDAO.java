package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.ItemsCarrito;


//interfaz publica para los items del carrito
public interface ItemsCarritoDAO extends GenericDAO<ItemsCarrito, Integer> {

    void ModificarItem(int idProducto, int idCarrito, int cantidad);
}
