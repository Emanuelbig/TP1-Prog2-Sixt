/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import dao.SixtDAO;
import dto.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import modelos.Usuario;

/**
 *
 * 
 */
public class SixtServicio {

    // Declaramos conexiones a las otras capas
    private final SixtDAO dao;
    private final AuthServicios AuthServicios;

    // Lista que esta en la ram mientras usamos el programa
    private List<Usuario> usuarios;
    // private List<Vehiculo> vehiculos; 
    // private List<Reserva> reservas;

    public SixtServicio() {
        this.dao = new SixtDAO();
        this.AuthServicios = new AuthServicios();

        // para cargar el txt en la memoria
        cargarDatosEnMemoria();
    }

    //aca esta el metodo que carga desarrollado
    private void cargarDatosEnMemoria() {
        this.usuarios = dao.leerUsuarios();

        // Si el txt no existe creamos un Admin por defecto para poder entrar
        if (this.usuarios.isEmpty()) {
            modelos.Administrador adminRoot = new modelos.Administrador("admin", "1234");
            this.usuarios.add(adminRoot);
            dao.guardarUsuarios(this.usuarios); // Lo guardamos en el txt
            System.out.println("Sistema inicializado: Se ha creado el usuario 'admin' con clave '1234'.");
        }
    }

    //metodo para iniciar sesion en el main
    public UsuarioDTO iniciarSesion(String username, String password) {
        return AuthServicios.login(username, password, this.usuarios);
    }

    //metodo para registrar un nuevo cliente
    public boolean registrarNuevoCliente(modelos.Cliente nuevoCliente) {
        // verificacion que no este en uso el nombre
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(nuevoCliente.getUsername())) {
                return false; // Ya existe ese nombre de usuario
            }
        }

        //se agrega mientras esta el programa en uso a la ram
        this.usuarios.add(nuevoCliente);

        // el dao pisa el texto con esta nueva info
        dao.guardarUsuarios(this.usuarios);

        return true;
    }
}
