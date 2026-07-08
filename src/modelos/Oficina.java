/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author
 */
public class Oficina {
    //atributos
    private String idOficina;
    private String nombre;
    private String direccion;

    //constructor
    public Oficina(String idOficina, String nombre, String direccion) {
        this.idOficina = idOficina;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    //getters
    public String getIdOficina() {
        return idOficina;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
    
    //setters

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //metodos
    public String toCSV() {
        return idOficina + ";" + nombre + ";" + direccion;
    }

    @Override
    public String toString() {
        return "Oficina " + nombre + " (" + idOficina + ")";
    }
}
