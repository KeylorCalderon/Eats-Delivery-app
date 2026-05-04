package com.example.eatsdeliveryapp.model;

//Clase de cuenta del admininistrador
public class CuentaAdministrador {

    //atributos propios de la clase
    private String idLaboral;
    private String contrasenna;

    //Constructor
    public CuentaAdministrador(){

    }

    //Otro constructor
    public CuentaAdministrador(String idLaboral, String contrasenna) {
        this.idLaboral = idLaboral;
        this.contrasenna = contrasenna;
    }

    //Getters and setters
    public String getIdLaboral() {
        return idLaboral;
    }

    public void setIdLaboral(String idLaboral) {
        this.idLaboral = idLaboral;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
}
