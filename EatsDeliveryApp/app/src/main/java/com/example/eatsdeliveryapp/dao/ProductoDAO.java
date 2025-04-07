package com.example.eatsdeliveryapp.dao;
import com.example.eatsdeliveryapp.model.Producto;

//interfaz publica para el producto dao
public interface ProductoDAO extends GenericDAO<Producto, Integer>{

    //funciones implementadas en su clase
    void EliminarProducto(int id);
    int ModificarProducto(Producto producto);
}
