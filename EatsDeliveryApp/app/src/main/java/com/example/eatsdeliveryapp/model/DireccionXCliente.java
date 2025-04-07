package com.example.eatsdeliveryapp.model;

//Clas publica de direccionxcliente
public class DireccionXCliente {

    //atributos propios de la clase
    private int IdCliente;
    private int IdDireccion;
    private String Activo;

    //Constructor
    public DireccionXCliente(int idCliente, int idDireccion, String activo) {
        IdCliente = idCliente;
        IdDireccion = idDireccion;
        Activo = activo;
    }

    public DireccionXCliente() {
    }

    //Getters and setters
    public int geIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        IdDireccion = idDireccion;
    }

    public String getActivo() {
        return Activo;
    }

    public void setActivo(String activo) {
        Activo = activo;
    }
}
