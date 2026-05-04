package com.example.eatsdeliveryapp.model;

//Clase publica de solicitu de Eliminacion para la base de datos
public class SolicitudEliminacion {

    //atributos de esta clase
    private int id;
    private int idRestaurante;
    private int idEncargado;
    private int activo;

    //constructor
    public SolicitudEliminacion(int id, int idRestaurante, int idEncargado, int activo) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.idEncargado = idEncargado;
        this.activo = activo;
    }

    public SolicitudEliminacion(){}

    //Getters and setters
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

    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
