package com.example.eatsdeliveryapp.model;

//Clase publica de itemsPedido estructura su tabla en la base
public class ItemsPedido {

    //atributos propios de la clase
    private int IdProducto;
    private int IdPedido;
    private int Cantidad;
    private int Activo;

    //Constructores
    public ItemsPedido(int idPedido, int cantidad, int activo) {
        IdPedido = idPedido;
        Cantidad = cantidad;
        Activo = activo;
    }

    //Constructor numero 2
    public ItemsPedido(int idProducto, int idPedido, int cantidad, int activo) {
        IdProducto = idProducto;
        IdPedido = idPedido;
        Cantidad = cantidad;
        Activo = activo;
    }

    public ItemsPedido() {
    }

    //Getters and setters
    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(int idPedido) {
        IdPedido = idPedido;
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
