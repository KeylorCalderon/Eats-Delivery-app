package com.example.eatsdeliveryapp.model;

//Clase de carrito para estructurar la tabla
public class Carrito {

    //atributos propios
    private int Id;
    private int IdCliente;
    private String FechaCreacion;
    private String FechaModificacion;

    //Constructor de la clase
    public Carrito(int id, int idCliente, String fechaCreacion, String fechaModificacion) {
        Id = id;
        IdCliente = idCliente;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }

    //segundo constructor
    public Carrito(int idCliente, String fechaCreacion, String fechaModificacion) {
        IdCliente = idCliente;
        FechaCreacion = fechaCreacion;
        FechaModificacion = fechaModificacion;
    }

    //Getters and setters
    public Carrito() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        FechaModificacion = fechaModificacion;
    }
}
