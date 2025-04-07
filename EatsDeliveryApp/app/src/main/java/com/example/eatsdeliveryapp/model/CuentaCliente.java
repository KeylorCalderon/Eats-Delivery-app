package com.example.eatsdeliveryapp.model;

//Clase publica de cuenta cliente
public class CuentaCliente {

    //atributos privados
    private String NombreUsuario;
    private String Contrasena;

    //Constructores
    public CuentaCliente(String nombreUsuario, String contrasena) {
        NombreUsuario = nombreUsuario;
        Contrasena = contrasena;
    }

    public CuentaCliente() {
    }

    //Getters and setters
    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
}