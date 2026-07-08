/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author
 */
public class Auto extends Vehiculo {
    //constructor que trae atributos heredados de vehiculo
    public Auto(String patente, String marca, String modelo, String color, double precioBaseDiario, Oficina oficinaActual) {
        super(patente, marca, modelo, color, precioBaseDiario, oficinaActual);
    }

    @Override
    public double calcularAlquiler(int dias) {
        return precioBaseDiario * dias; // El auto no tiene recargos
    }

    @Override
    public String toCSV() {
        return "AUTO;" + patente + ";" + marca + ";" + modelo + ";" + color + ";" + precioBaseDiario + ";" + oficinaActual.getIdOficina();
    }

    @Override
    public String toString() {
        return "Auto [" + patente + "] " + marca + " " + modelo;
    }
}
