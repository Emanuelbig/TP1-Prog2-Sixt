/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import dto.ClienteDTO;
import dto.UsuarioDTO;
import java.util.Scanner;
import servicios.SixtServicio;
/**
 * Menu del programa
 * 
 */
public class Sixt {

   
    public static void main(String[] args) {
        // creamos el objeto scanner para leer el teclado
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cargando el sistema y leyendo la base de datos...\n\n");
        
        //para activar el programa, lee los txt y los pone en la ram
        SixtServicio SixtServicio = new SixtServicio();
        
        boolean salirDelPrograma = false;
        
        while (!salirDelPrograma) {
            System.out.println("BIENVENIDO A SIXT, ALQUILA TU AUTO :)");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Salir del sistema");
            System.out.println("Seleccione una opcion:\n\n");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); //con este scanner limpio el enter, sino me tiro usuario y contraseña al mismo tiempo
            
            switch (opcion) {
                case 1:
                    System.out.println("Usuario:");
                    String user =scanner.nextLine();
                    System.out.println("Contraseña:");
                    String pass =scanner.nextLine();
                    
                    UsuarioDTO usuarioLogueado = SixtServicio.iniciarSesion(user, pass);
                    
                    if (usuarioLogueado == null) {
                        System.out.println("Error: Credenciales incorrectas. Intente nuevamente.");
                    } else {
                        System.out.println("Login exitoso!");
                        
                        // Si entra, lo mandamos a su menú específico
                        //mostrarMenuSegunRol(usuarioLogueado, scanner, SixtServicio);
                    }
                    break;
                case 2:
                    System.out.println("Guardando cambios, saliento del sistema..");
                    salirDelPrograma = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        
        scanner.close(); // buena practica de prog cerrar el escaner al final
    }
    
}
