/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author
 */
public class Camioneta extends Vehiculo {
    //aca si hay un recargo, ponemos un atributo
    private double recargoCapacidad;

    //constructor que hereda de vehiculo + atributo recargoCapacidad
    public Camioneta(String patente, String marca, String modelo, String color, double precioBaseDiario, Oficina oficinaActual, double recargoCapacidad) {
        super(patente, marca, modelo, color, precioBaseDiario, oficinaActual);
        this.recargoCapacidad = recargoCapacidad;
    }

    //getter
    public double getRecargoCapacidad() { 
        return recargoCapacidad; 
    }
    
    //setter
    public void setRecargoCapacidad(double recargoCapacidad) { 
        this.recargoCapacidad = recargoCapacidad; 
    }

    //metodo rescrito (polimorfismo)
    @Override
    public double calcularAlquiler(int dias) {
        return (precioBaseDiario * dias) + recargoCapacidad; // Suma el recargo
    }

    @Override
    public String toCSV() {
        return "CAMIONETA;" + patente + ";" + marca + ";" + modelo + ";" + color + ";" + precioBaseDiario + ";" + oficinaActual.getIdOficina() + ";" + recargoCapacidad;
    }

    @Override
    public String toString() {
        return "Camioneta [" + patente + "] " + marca + " " + modelo;
    }
}
