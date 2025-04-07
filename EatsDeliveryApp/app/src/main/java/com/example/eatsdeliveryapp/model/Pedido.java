package com.example.eatsdeliveryapp.model;

//Clase de pedido, estructura su tabla en sqlite
public class Pedido {

    //atributos propios de la clase
    private int Id;
    private String IdEstado;
    private int IdCliente;
    private String Fecha;
    private float TotalPagado;
    private int Direccion;

    //Constructor principal
    public Pedido(int id, String idEstado, int direccion, int idCliente, String fecha, float totalPagado) {
        Id = id;
        IdEstado = idEstado;
        IdCliente = idCliente;
        Fecha = fecha;
        Direccion=direccion;
        TotalPagado = totalPagado;
    }

    //Segundo constructor
    public Pedido(String idEstado, int direccion, int idCliente, String fecha, float totalPagado) {
        IdEstado = idEstado;
        IdCliente = idCliente;
        Fecha = fecha;
        Direccion=direccion;
        TotalPagado = totalPagado;
    }

    public Pedido() {
    }

    //Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDireccion() {
        return Direccion;
    }

    public void setDireccion(int direccion) {
        Direccion = direccion;
    }

    public String getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(String idEstado) {
        IdEstado = idEstado;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public float getTotalPagado() {
        return TotalPagado;
    }

    public void setTotalPagado(float totalPagado) {
        TotalPagado = totalPagado;
    }
}
