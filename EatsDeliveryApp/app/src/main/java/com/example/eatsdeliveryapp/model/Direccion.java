package com.example.eatsdeliveryapp.model;

//Clase propia de direccion, para conformar la tabla en sqlite
public class Direccion {

    //atributos de esta clase
    private String Distrito;
    private String Canton;
    private String Provincia;
    private String DireccionExacta;
    private int Id;

    //Constructor
    public Direccion(String distrito, String canton, String provincia, String direccionExacta) {
        Distrito = distrito;
        Canton = canton;
        Provincia = provincia;
        DireccionExacta = direccionExacta;
    }

    public Direccion() {
    }

    //Getters and setters
    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getCanton() {
        return Canton;
    }

    public void setCanton(String canton) {
        Canton = canton;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }

    public String getDireccionExacta() {
        return DireccionExacta;
    }

    public void setDireccionExacta(String direccionExacta) {
        DireccionExacta = direccionExacta;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
