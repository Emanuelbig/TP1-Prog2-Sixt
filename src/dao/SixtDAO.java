/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.IOException; //para los errores (try catch and finally)
import java.nio.file.Files; //para la persistencia de datos
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList; // 
import java.util.List;
import modelos.*; // para importar todas las clases

/**
 * Esta clase esta encargada de la persistencia de datos en el archivo.txt
 * 
 */
public class SixtDAO {
    //Creamos y definimos las rutas relativas de los archivos. 
    //Se van a crear solos en la raíz del proyecto si no existen.
    private final Path RUTA_USUARIOS = Paths.get("usuarios.txt");
    private final Path RUTA_VEHICULOS = Paths.get("vehiculos.txt");
    private final Path RUTA_RESERVAS = Paths.get("reservas.txt");
    private final Path RUTA_OFICINAS = Paths.get("oficinas.txt");

    // SECCIÓN USUARIOS
 
    
    //Recibe la lista completa de usuarios del sistema y la guarda en el txt.
    public void guardarUsuarios(List<Usuario> usuarios) {
        List<String> lineas = new ArrayList<>();
        
        //Usamos un for each para leer cada objeto y Convertirlo en una linea de datos usando el metodo toCSV()
        for (Usuario u : usuarios) {
            lineas.add(u.toCSV());
        }

        try {
            // java.nio 
            Files.write(RUTA_USUARIOS, lineas);
        } catch (IOException e) {
            System.out.println("Error crítico al guardar los usuarios: " + e.getMessage());
        }
    }

    
    //Leemos el txt de usuarios y reconstruye los objetos correspondientes. 
    public List<Usuario> leerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        //Comprobacion para ver si el archivo existe (ej: es la primera vez que abrimos el programa), devuelvo la lista vacía
        if (!Files.exists(RUTA_USUARIOS)) {
            return usuarios;
        }

        try {
            List<String> lineas = Files.readAllLines(RUTA_USUARIOS);

            //con este for each + el split separamos los datos
            for (String linea : lineas) {
                // Separamos los datos por el punto y coma que definiste en tus toCSV()
                String[] datos = linea.split(";");
                
                String rol = datos[0];
                String username = datos[1];
                String password = datos[2];

                // definimos segun el rol 
                if (rol.equals("ADMIN")) {
                    usuarios.add(new Administrador(username, password));
                    
                } else if (rol.equals("VENDEDOR")) {
                    usuarios.add(new Vendedor(username, password));
                    
                } else if (rol.equals("CLIENTE")) {
                    // aca es donte toCSV se parte
                    String codigoUnico = datos[3];
                    String dni = datos[4];
                    String nombre = datos[5];
                    String direccion = datos[6];
                    String email = datos[7];
                    String telefono = datos[8];
                    
                    usuarios.add(new Cliente(username, password, codigoUnico, dni, nombre, direccion, email, telefono));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }

        return usuarios;
    }
    
    
    // SECCIÓN OFICINAS 
    
    // SECCIÓN VEHÍCULOS 
    
    
    // tenemos que poner los métodos guardarOficinas(), leerOficinas(), 
    // guardarVehiculos() y leerVehiculos() con la misma logica.
}
