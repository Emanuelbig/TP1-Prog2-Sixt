/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author 
 */
public class Administrador extends Usuario {
    
    //Constructor con super que toma los datos de la clase padre
    public Administrador(String username, String password) {
        super(username, password);
    }

    //Sobreescribimos metodos
    @Override
    public String getRol() {
        return "ADMIN";
    }

    @Override
    public String toCSV() {
        return getRol() + ";" + username + ";" + password;
    }

    @Override
    public String toString() {
        return "Administrador [" + username + "]";
    }
}
