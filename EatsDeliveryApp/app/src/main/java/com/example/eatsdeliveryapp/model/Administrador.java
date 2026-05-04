package com.example.eatsdeliveryapp.model;

import java.io.Serializable;

//Clase publica del administrador para la estructura de la tabla
public class Administrador implements Serializable {

    //atributos propios de la clase
    private int id;
    private int idRestaurante;
    private String idLaboral;
    private int idPuesto;
    private int Cedula;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    //Constructor
    public Administrador(int id, int idRestaurante, String idLaboral, int idPuesto, int cedula, String nombre, String primerApellido, String segundoApellido) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.idLaboral = idLaboral;
        this.idPuesto = idPuesto;
        Cedula = cedula;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    //Getters and setters
    public Administrador(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getIdLaboral() {
        return idLaboral;
    }

    public void setIdLaboral(String idLaboral) {
        this.idLaboral = idLaboral;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public int getCedula() {
        return Cedula;
    }

    public void setCedula(int cedula) {
        Cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
}
