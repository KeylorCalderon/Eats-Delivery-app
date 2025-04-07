package com.example.eatsdeliveryapp.model;


//Clase publica de estadoopedido, estructura la tabla de la base
public class EstadoPedido {

    //atributos privados de la clase
    private int Id;
    private String Nombre;

    //Constructor
    public EstadoPedido(int id, String nombre) {
        Id = id;
        Nombre = nombre;
    }

    public EstadoPedido(String nombre) {
        Nombre = nombre;
    }

    public EstadoPedido() {
    }

    //Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
