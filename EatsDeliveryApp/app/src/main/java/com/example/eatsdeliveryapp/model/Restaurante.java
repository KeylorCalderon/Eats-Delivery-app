package com.example.eatsdeliveryapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

//Clase publica de restaurante, serializable por las imagenes
public class Restaurante implements Serializable {

    //atributos propios de la clase
    private int Id;
    private int IdDireccion;
    private String Nombre;
    private String Categoria;
    private Bitmap Foto;
    private int Activo;

    //Constructor
    public Restaurante(int id, int idDireccion, String nombre, String categoria, byte[] foto, int activo) {
        Id = id;
        IdDireccion=idDireccion;
        Nombre = nombre;
        Categoria = categoria;
        ByteArrayInputStream bais = new ByteArrayInputStream(foto);
        Foto = BitmapFactory.decodeStream(bais);
        Activo=activo;
    }

    //Getters and setters
    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public Restaurante() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        IdDireccion = idDireccion;
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

    public int getActivo() {
        return Activo;
    }

    public void setActivo(int activo) {
        Activo = activo;
    }
}
