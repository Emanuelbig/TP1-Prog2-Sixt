/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author 
 */
public class Cliente extends Usuario {
    //Atributos
    private String codigoUnico;
    private String dni;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    //Constructor
    public Cliente(String username, String password, String codigoUnico, String dni, String nombre, String direccion, String email, String telefono) {
        super(username, password);
        this.codigoUnico = codigoUnico;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    //getters
    public String getCodigoUnico() {
        return codigoUnico;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
    
    //setters
    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    //sobreescribimos metodos
    @Override
    public String getRol() {
        return "CLIENTE";
    }

    @Override
    public String toCSV() {
        // Formato: ROL;user;pass;codigo;dni;nombre;direccion;email;telefono
        return getRol() + ";" + username + ";" + password + ";" + codigoUnico + ";" + dni + ";" + nombre + ";" + direccion + ";" + email + ";" + telefono;
    }

    @Override
    public String toString() {
        return "Cliente [" + codigoUnico + "] " + nombre + " | DNI: " + dni;
    }
}
