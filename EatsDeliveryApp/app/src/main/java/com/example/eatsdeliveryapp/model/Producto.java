package com.example.eatsdeliveryapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

//Clase publica de producto, para la base de datos
public class Producto {

    //atributos de esta clase
    private int Id;
    private int IdRestaurante;
    private String Nombre;
    private Bitmap Foto;
    private String Descripcion;
    private Float Precio;
    private int Cantidad;
    private int Activo;

    //Constructor
    public Producto(int id, int idRestaurante, String nombre, byte[] foto, String descripcion, Float precio, int cantidad, int activo) {
        Id = id;
        IdRestaurante = idRestaurante;
        Nombre = nombre;
        ByteArrayInputStream bais = new ByteArrayInputStream(foto);
        Foto = BitmapFactory.decodeStream(bais);
        Descripcion = descripcion;
        Precio = precio;
        Cantidad = cantidad;
        Activo = activo;
    }

    //Getters and setters
    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getIdRestaurante() {
        return IdRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        IdRestaurante = idRestaurante;
    }

    public int getActivo() {
        return Activo;
    }

    public void setActivo(int activo) {
        Activo = activo;
    }

    public Producto() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Bitmap getFoto() {
        return Foto;
    }

    public void setFoto(Bitmap foto) {
        Foto = foto;
    }

    public void setFoto(byte[] foto) {
        ByteArrayInputStream bais = new ByteArrayInputStream(foto);
        Foto = BitmapFactory.decodeStream(bais);
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Float getPrecio() {
        return Precio;
    }

    public void setPrecio(Float precio) {
        Precio = precio;
    }
}
