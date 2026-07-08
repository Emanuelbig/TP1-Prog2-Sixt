/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author
 */
public abstract class Vehiculo {
    //atributos
    protected String patente;
    protected String marca;
    protected String modelo;
    protected String color;
    protected double precioBaseDiario;
    protected Oficina oficinaActual;

    //constructor
    public Vehiculo(String patente, String marca, String modelo, String color, double precioBaseDiario, Oficina oficinaActual) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.precioBaseDiario = precioBaseDiario;
        this.oficinaActual = oficinaActual;
    }

    //getters

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public double getPrecioBaseDiario() {
        return precioBaseDiario;
    }

    public Oficina getOficinaActual() {
        return oficinaActual;
    }
    
    //setters

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrecioBaseDiario(double precioBaseDiario) {
        this.precioBaseDiario = precioBaseDiario;
    }

    public void setOficinaActual(Oficina oficinaActual) {
        this.oficinaActual = oficinaActual;
    }
    

    //metodos polimorfismo
    public abstract double calcularAlquiler(int dias);
    public abstract String toCSV();
}
