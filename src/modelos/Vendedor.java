/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author 
 */
public class Vendedor extends Usuario {
    
    //Constructor con super que toma los datos de la clase padre
    public Vendedor(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRol() {
        return "VENDEDOR";
    }

    @Override
    public String toCSV() {
        return getRol() + ";" + username + ";" + password;
    }

    @Override
    public String toString() {
        return "Vendedor [" + username + "]";
    }
}
