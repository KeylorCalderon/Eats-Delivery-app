package com.example.eatsdeliveryapp.model;

//Clase de puesto para la base de datos
public class Puesto {

    //atributos de la clase
    private int id;
    private String nombre;

    //Constructor
    public Puesto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Puesto(){}

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
