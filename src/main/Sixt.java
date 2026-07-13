/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import dto.UsuarioDTO;
import java.util.Scanner;
import servicios.SixtServicio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu del programa
 * 
 * Grupo H, Programacion 2, Turno Noche, Año 2026
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
            //System.out.println("Seleccione una opcion:\n\n");

            int opcion = pedirEntero(scanner, "Elegi una opcion: \n\n");
            //int opcion = scanner.nextInt();
            //scanner.nextLine(); //con este scanner limpio el enter, sino me tiro usuario y contraseña al mismo tiempo

            switch (opcion) {
                case 1:
                    System.out.println("Usuario:");
                    String user = scanner.nextLine();
                    System.out.println("Password:");
                    String pass = scanner.nextLine();

                    UsuarioDTO usuarioLogueado = SixtServicio.iniciarSesion(user, pass);

                    if (usuarioLogueado == null) {
                        System.out.println("Error: Credenciales incorrectas. Intente nuevamente.");
                    } else {
                        System.out.println("Login exitoso! Bienvenido, " + usuarioLogueado.getUsername());
                        switch (usuarioLogueado.getRol()) {
                            case "ADMIN":
                                menuAdmin(scanner, SixtServicio);
                                break;
                            case "VENDEDOR":
                                menuVendedor(scanner, SixtServicio);
                                break;
                            case "CLIENTE":
                                menuCliente(scanner, SixtServicio, usuarioLogueado);
                                break;
                            default:
                                System.out.println("Rol desconocido.");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Guardando cambios, saliento del sistema..");
                    salirDelPrograma = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }

        scanner.close(); // buena practica de prog cerrar el escaner al final
    }

    static void pausar(Scanner scanner) {
        System.out.println("\nPresiona ENTER para volver al menu...");
        scanner.nextLine();
    }
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static void menuAdmin(Scanner scanner, servicios.SixtServicio servicio) {
        int opcion;
        do {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Agregar oficina");
            System.out.println("2. Gestionar vehiculos");
            System.out.println("3. Agregar usuarios");
            System.out.println("4. Ver reservas");
            System.out.println("5. Ver vehiculos");
            System.out.println("6. Cerrar sesion");
            //System.out.print("Elegí una opción: ");
            opcion = pedirEntero(scanner, "Elegi una opcion: ");
            //opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarOficina(scanner, servicio);
                    pausar(scanner);
                    break;
                case 2:
                    agregarVehiculo(scanner, servicio);
                    pausar(scanner);
                    break;
                case 3:
                    agregarUsuario(scanner, servicio);
                    pausar(scanner);
                    break;
                case 4:
                    listarReservas(servicio);
                    pausar(scanner);
                    break;
                case 5:
                    listarVehiculos(servicio);
                    pausar(scanner);
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion inválida");
                    break;
            }
        } while (opcion != 6);
    }

    static void menuVendedor(Scanner scanner, servicios.SixtServicio servicio) {
        int opcion;
        do {
            System.out.println("\n--- MENU VENDEDOR ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Crear reservas");
            System.out.println("3. Marcar reserva como entregada");
            System.out.println("4. Ver vehiculos disponibles");
            System.out.println("5. Cerrar sesion");
            //System.out.print("Elegí una opción: ");
            //opcion = Integer.parseInt(scanner.nextLine());
            opcion = pedirEntero(scanner, "Elegi una opcion: ");

            switch (opcion) {
                case 1:
                    agregarCliente(scanner, servicio);
                    pausar(scanner);
                    break;
                case 2:
                    agregarReserva(scanner, servicio);
                    pausar(scanner);
                    break;
                case 3:
                    marcarEntrega(scanner, servicio);
                    pausar(scanner);
                    break;
                case 4:
                    verDisponibles(scanner, servicio);
                    pausar(scanner);
                    break;
                case 5:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcion != 5);
    }

    static void menuCliente(Scanner scanner, servicios.SixtServicio servicio, dto.UsuarioDTO usuario) {
        int opcion;
        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Ver mis reservas");
            System.out.println("2. Ver mi estado/deuda");
            System.out.println("3. Cerrar sesion");
            //System.out.print("Elegí una opción: ");
            //opcion = Integer.parseInt(scanner.nextLine());

            opcion = pedirEntero(scanner, "Elegi una opcion: ");

            switch (opcion) {
                case 1:
                    verMisReservas(servicio, usuario);
                    pausar(scanner);
                    break;
                case 2:
                    verMiInfo(usuario);
                    pausar(scanner);
                    break;
                case 3:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opción invalida");
                    break;
            }
        } while (opcion != 3);
    }

    static void agregarOficina(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.print("Nombre de la oficina: ");
        String nombre = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        boolean exito = servicio.registrarNuevaOficina(nombre, direccion);

        if (exito) {
            System.out.println("Oficina agregada correctamente.");
        } else {
            System.out.println("Error: ya existe una oficina con ese nombre.");
        }
    }

    static void agregarVehiculo(Scanner scanner, servicios.SixtServicio servicio) {
        if (servicio.getOficinas().isEmpty()) {
            System.out.println("No hay oficinas cargadas. Primero carga una oficina para poder dar de alta un vehiculo.");
            return;
        }

        System.out.println("1. Auto");
        System.out.println("2. Camioneta");
        System.out.print("Tipo de vehiculo: ");
        int tipoOpcion = Integer.parseInt(scanner.nextLine());
        String tipo = (tipoOpcion == 1) ? "AUTO" : "CAMIONETA";

        System.out.print("Patente: ");
        String patente = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        //System.out.print("Precio base diario: ");
        //double precio = Double.parseDouble(scanner.nextLine());
        double precio = pedirDouble(scanner, "Precio base diario: ");

        System.out.println("Oficinas disponibles:");
        for (modelos.Oficina o : servicio.getOficinas()) {
            System.out.println(o);
        }
        System.out.print("ID de oficina: ");
        int idOficina = Integer.parseInt(scanner.nextLine());

        double recargo = 0;
        if (tipoOpcion == 2) {
            System.out.print("Recargo por capacidad: ");
            recargo = Double.parseDouble(scanner.nextLine());
        }

        boolean exito = servicio.registrarNuevoVehiculo(tipo, patente, marca, modelo, color, precio, idOficina, recargo);

        if (exito) {
            System.out.println("Vehiculo agregado correctamente.");
        } else {
            System.out.println("Error: revise que la oficina exista y la patente no este repetida.");
        }
    }

    static void agregarReserva(Scanner scanner, servicios.SixtServicio servicio) {
        LocalDate fechaInicio, fechaFin;
        try {
            System.out.print("Fecha de inicio (DD/MM/YYYY): ");
            fechaInicio = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);
            System.out.print("Fecha de fin (DD/MM/YYYY): ");
            fechaFin = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);
        } catch (Exception e) {
            System.out.println("Fecha invalida. Usa el formato DD/MM/YYYY.");
            return;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la de inicio.");
            return;
        }

        List<modelos.Vehiculo> disponibles = servicio.getVehiculosDisponibles(fechaInicio, fechaFin);
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehiculos disponibles para ese rango de fechas.");
            return;
        }

        System.out.println("Vehiculos disponibles:");
        for (modelos.Vehiculo v : disponibles) {
            //Mostramos el ID de forma bien clara antes del texto del auto
            System.out.println("ID: " + v.getId() + " - " + v.toString());
        }

        List<Integer> idsElegidos = new ArrayList<>();
        boolean seguirAgregando = true;
        while (seguirAgregando) {
            int idVehiculo = pedirEntero(scanner, "ID de vehiculo a agregar: ");
            
            //Verificamos que el ID tipeado exista realmente en la lista de disponibles
            boolean existe = false;
            for (modelos.Vehiculo v : disponibles) {
                if (v.getId() == idVehiculo) existe = true;
            }
            
            if (!existe) {
                System.out.println("--> ERROR: El ID ingresado no esta en la lista de disponibles.");
                continue; // Lo hace volver a pedir el ID
            }
            
            idsElegidos.add(idVehiculo);

            //Obligamos a que la respuesta sea estrictamente 's' o 'n'
            String respuesta = "";
            while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                System.out.print("Agregar otro vehiculo? (s/n): ");
                respuesta = scanner.nextLine();
            }
            seguirAgregando = respuesta.equalsIgnoreCase("s");
        }

        //Aviso preventivo para el usuario
        System.out.println("\n(Aviso: El cliente ya debe estar registrado en el sistema)");
        System.out.print("DNI del cliente: ");
        String dniCliente = scanner.nextLine();

        System.out.println("Oficinas disponibles:");
        for (modelos.Oficina o : servicio.getOficinas()) {
            System.out.println(o); // Si acá también te faltan los IDs, cambialo a: System.out.println("ID: " + o.getIdOficina() + " - " + o.toString());
        }

        int idOficinaDestino = pedirEntero(scanner, "ID de oficina destino: ");
        double litros = pedirDouble(scanner, "Litros de gasolina inicial: ");

        boolean exito = servicio.registrarNuevaReserva(dniCliente, idsElegidos, fechaInicio, fechaFin, idOficinaDestino, litros);

        if (exito) {
            System.out.println("Reserva registrada correctamente.");
        } else {
            // MEJORA 5: Mensaje de error desglosado
            System.out.println("--> ERROR AL REGISTRAR RESERVA. Por favor revise:");
            System.out.println("  1. Que el DNI (" + dniCliente + ") pertenezca a un Cliente ya registrado.");
            System.out.println("  2. Que la oficina de destino exista.");
        }
    }

    static void agregarCliente(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        boolean exito = servicio.registrarNuevoCliente(username, password, dni, nombre, direccion, email, telefono);

        if (exito) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("Error: ya existe un usuario con ese nombre o un cliente con ese DNI.");
        }
    }

    static void marcarEntrega(Scanner scanner, servicios.SixtServicio servicio) {
        List<modelos.Reserva> pendientes = servicio.getReservasPendientes();

        if (pendientes.isEmpty()) {
            System.out.println("No hay reservas pendientes de entrega.");
            return;
        }

        System.out.println("Reservas pendientes:");
        for (modelos.Reserva r : pendientes) {
            System.out.println(r);
        }

        System.out.print("ID de reserva a marcar como entregada: ");
        int idReserva = Integer.parseInt(scanner.nextLine());

        boolean exito = servicio.marcarReservaComoEntregada(idReserva);

        if (exito) {
            System.out.println("Reserva marcada como entregada. Los vehiculos ya estan disponibles en la oficina destino.");
        } else {
            System.out.println("Error: la reserva no existe o ya estaba entregada.");
        }
    }

    static void verMisReservas(servicios.SixtServicio servicio, dto.UsuarioDTO usuario) {
        dto.ClienteDTO cliente = (dto.ClienteDTO) usuario;
        List<modelos.Reserva> misReservas = servicio.getReservasPorCliente(cliente.getId());

        if (misReservas.isEmpty()) {
            System.out.println("No tenes reservas registradas.");
        } else {
            System.out.println("--- TUS RESERVAS ---");
            for (modelos.Reserva r : misReservas) {
                System.out.println(r);
            }
        }
    }

    static void verMiInfo(dto.UsuarioDTO usuario) {
        dto.ClienteDTO cliente = (dto.ClienteDTO) usuario;
        System.out.println("--- TU INFORMACION ---");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Usuario: " + cliente.getUsername());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("DNI: " + cliente.getDni());
    }

    static void agregarUsuario(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.println("1. Vendedor");
        System.out.println("2. Administrador");
        System.out.print("Tipo de usuario: ");
        int tipoOpcion = pedirEntero(scanner, "Elegi una opcion: ");
        //int tipoOpcion = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean exito;
        if (tipoOpcion == 1) {
            exito = servicio.registrarNuevoVendedor(username, password);
        } else {
            exito = servicio.registrarNuevoAdministrador(username, password);
        }

        if (exito) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("Error: ya existe un usuario con ese nombre.");
        }
    }

    static void listarVehiculos(servicios.SixtServicio servicio) {
        List<modelos.Vehiculo> vehiculos = servicio.getVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos cargados.");
        } else {
            System.out.println("--- VEHICULOS ---");
            for (modelos.Vehiculo v : vehiculos) {
                System.out.println(v);
            }
        }
    }

    static void listarReservas(servicios.SixtServicio servicio) {
        List<modelos.Reserva> reservas = servicio.getReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas cargadas.");
        } else {
            System.out.println("--- RESERVAS ---");
            for (modelos.Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    static void verDisponibles(Scanner scanner, servicios.SixtServicio servicio) {
        LocalDate fechaInicio, fechaFin;
        try {
            System.out.print("Fecha de inicio (DD/MM/YYYY): ");
            fechaInicio = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);
            System.out.print("Fecha de fin (DD/MM/YYYY): ");
            fechaFin = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);
        } catch (Exception e) {
            System.out.println("Fecha invalida. Usa el formato DD/MM/YYYY.");
            return;
        }

        List<modelos.Vehiculo> disponibles = servicio.getVehiculosDisponibles(fechaInicio, fechaFin);
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehiculos disponibles para ese rango de fechas.");
        } else {
            System.out.println("--- VEHICULOS DISPONIBLES ---");
            for (modelos.Vehiculo v : disponibles) {
                System.out.println(v);
            }
        }
    }

    //para problemas con el teclado
    static int pedirEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingresa un numero entero valido.");
            }
        }
    }

    static double pedirDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Por favor ingresa un numero decimal valido (Ej: 10.5).");
            }
        }
    }
}
