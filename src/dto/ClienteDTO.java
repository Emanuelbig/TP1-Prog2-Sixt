/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 *
 */
public class ClienteDTO extends UsuarioDTO {

    //Atributos
    private String dni;
    private String nombre;
    private String codigoUnico;

    //Constructor
    public ClienteDTO(String username, String rol, String dni, String nombre, String codigoUnico) {
        super(username, rol);
        this.dni = dni;
        this.nombre = nombre;
        this.codigoUnico = codigoUnico;
    }

    //getters
    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }
}
