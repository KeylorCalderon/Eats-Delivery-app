package com.example.eatsdeliveryapp.model;

//Clase publica de tarjeta, para la estructura de su tabla
public class Tarjeta {

    //atributos de la clase
    private int id;
    private int idCliente;
    private int numero;
    private String nombreTitural;
    private String fechaVencimiento;
    private int codigoSeguridad;


    //Constructor
    public Tarjeta(int id, int idCliente, int numero, String nombreTitural, String fechaVencimiento, int codigoSeguridad) {
        this.id = id;
        this.idCliente = idCliente;
        this.numero = numero;
        this.nombreTitural = nombreTitural;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoSeguridad = codigoSeguridad;
    }

    public Tarjeta() {

    }

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

    public String getNombreTitural() {
        return nombreTitural;
    }

    public void setNombreTitural(String nombreTitural) {
        this.nombreTitural = nombreTitural;
    }

    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
}
