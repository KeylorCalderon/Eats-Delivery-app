package com.example.eatsdeliveryapp.model;

//Clase de lciente publica, estructura la tabla de la base
public class Cliente {

    //atributos de la clase
    private int Id;
    private String NombreUsuario;
    private String Nombre;
    private String PrimerApellido;
    private String SegundoApellido;
    private String Correo;

    //Constructor
    public Cliente(int id, String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String correo) {
        Id = id;
        NombreUsuario = nombreUsuario;
        Nombre = nombre;
        PrimerApellido = primerApellido;
        SegundoApellido = segundoApellido;
        Correo = correo;
    }

    public Cliente() { }

    //Getters and setters
    public int getId() { return Id; }

    public void setId(int id) {
        Id = id;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}