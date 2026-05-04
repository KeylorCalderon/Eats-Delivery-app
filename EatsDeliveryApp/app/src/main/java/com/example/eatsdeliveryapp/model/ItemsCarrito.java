package com.example.eatsdeliveryapp.model;


//Clase publica para los items del carrito, mejoran la estructura de la tabla
public class ItemsCarrito {

    //items propios de la clase
    private int IdProducto;
    private int IdCarrito;
    private int Cantidad;
    private int Activo;

    //Constructores
    public ItemsCarrito(int idProducto, int idCarrito, int cantidad, int activo) {
        IdProducto = idProducto;
        IdCarrito = idCarrito;
        Cantidad = cantidad;
        Activo = activo;
    }

    public ItemsCarrito() {
    }


    //Getters and setters
    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getIdCarrito() {
        return IdCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        IdCarrito = idCarrito;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getActivo() {
        return Activo;
    }

    public void setActivo(int activo) {
        Activo = activo;
    }
}
