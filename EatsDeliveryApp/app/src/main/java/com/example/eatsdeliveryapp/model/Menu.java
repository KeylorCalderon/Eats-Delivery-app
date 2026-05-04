package com.example.eatsdeliveryapp.model;

//Clase menu, estructura esta tabla en la base
public class Menu {

    //atributos de la clase
    private int Id;
    private int IdRestaurante;

    //constructor
    public Menu(int id, int idRestaurante) {
        Id = id;
        IdRestaurante = idRestaurante;
    }

    //otros constructores
    public Menu(int idRestaurante) {
        IdRestaurante = idRestaurante;
    }

    public Menu() {
    }

    //Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdRestaurante() {
        return IdRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        IdRestaurante = idRestaurante;
    }
}
