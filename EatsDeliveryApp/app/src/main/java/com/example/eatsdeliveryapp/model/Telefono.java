package com.example.eatsdeliveryapp.model;

//Clase publica de telefono, para la tabla en la base de datos
public class Telefono {

    //Atributos privados de la clase
    private int id;
    private int idCliente;
    private int numero;


    //Constructor
    public Telefono(int id, int idCliente, int numero) {
        this.id = id;
        this.idCliente = idCliente;
        this.numero = numero;
    }

    public Telefono(){}

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
