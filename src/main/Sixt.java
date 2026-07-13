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
                    String user = scanner.nextLine();
                    System.out.println("Contraseña:");
                    String pass = scanner.nextLine();

                    UsuarioDTO usuarioLogueado = SixtServicio.iniciarSesion(user, pass);

                    if (usuarioLogueado == null) {
                        System.out.println("Error: Credenciales incorrectas. Intente nuevamente.");
                    } else {
                        System.out.println("Login exitoso! Bienvenido, " + usuarioLogueado.getUsername());
                        switch (usuarioLogueado.getRol()) {
                            case "ADMIN" ->
                                menuAdmin(scanner, SixtServicio);
                            case "VENDEDOR" ->
                                menuVendedor(scanner, SixtServicio);
                            case "CLIENTE" ->
                                menuCliente(scanner, SixtServicio, usuarioLogueado);
                            default ->
                                System.out.println("Rol desconocido.");
                        }
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

    static void pausar(Scanner scanner) {
        System.out.println("\nPresioná ENTER para volver al menú...");
        scanner.nextLine();
    }
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static void menuAdmin(Scanner scanner, servicios.SixtServicio servicio) {
        int opcion;
        do {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Agregar oficina");
            System.out.println("2. Gestionar vehículos");
            System.out.println("3. Agregar usuarios");
            System.out.println("4. Ver reservas");
            System.out.println("5. Cerrar sesión");
            System.out.print("Elegí una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    agregarOficina(scanner, servicio);
                    pausar(scanner);
                }
                case 2 -> {
                    agregarVehiculo(scanner, servicio);
                    pausar(scanner);
                }
                case 3 -> {
                    agregarUsuario(scanner, servicio);
                    pausar(scanner);
                }
                case 4 -> {
                    listarReservas(servicio);
                    pausar(scanner);
                }
                case 5 -> {
                    listarVehiculos(servicio);
                    pausar(scanner);
                }
                case 6 ->
                    System.out.println("Cerrando sesión...");
                default ->
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    static void menuVendedor(Scanner scanner, servicios.SixtServicio servicio) {
        int opcion;
        do {
            System.out.println("\n--- MENU VENDEDOR ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Crear reservas");
            System.out.println("3. Marcar reserva como entregada");
            System.out.println("4. Ver vehículos disponibles");
            System.out.println("5. Cerrar sesión");
            System.out.print("Elegí una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    agregarCliente(scanner, servicio);
                    pausar(scanner);
                }
                case 2 -> {
                    agregarReserva(scanner, servicio);
                    pausar(scanner);
                }
                case 3 -> {
                    marcarEntrega(scanner, servicio);
                    pausar(scanner);
                }
                case 4 -> {
                    verDisponibles(scanner, servicio);
                    pausar(scanner);
                }
                case 5 ->
                    System.out.println("Cerrando sesión...");
                default ->
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    static void menuCliente(Scanner scanner, servicios.SixtServicio servicio, dto.UsuarioDTO usuario) {
        int opcion;
        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Ver mis reservas");
            System.out.println("2. Ver mi estado/deuda");
            System.out.println("3. Cerrar sesión");
            System.out.print("Elegí una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> {
                    verMisReservas(servicio, usuario);
                    pausar(scanner);
                }
                case 2 -> {
                    verMiInfo(usuario);
                    pausar(scanner);
                }
                case 3 ->
                    System.out.println("Cerrando sesión...");
                default ->
                    System.out.println("Opción inválida");
            }
        } while (opcion != 3);
    }

    static void agregarOficina(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.print("Nombre de la oficina: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
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
            System.out.println("No hay oficinas cargadas. Primero cargá una oficina para poder dar de alta un vehículo.");
            return;
        }

        System.out.println("1. Auto");
        System.out.println("2. Camioneta");
        System.out.print("Tipo de vehículo: ");
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
        System.out.print("Precio base diario: ");
        double precio = Double.parseDouble(scanner.nextLine());

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
            System.out.println("Vehículo agregado correctamente.");
        } else {
            System.out.println("Error: revisá que la oficina exista y la patente no esté repetida.");
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
            System.out.println("Fecha inválida. Usá el formato DD/MM/YYYY.");
            return;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la de inicio.");
            return;
        }

        List<modelos.Vehiculo> disponibles = servicio.getVehiculosDisponibles(fechaInicio, fechaFin);
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles para ese rango de fechas.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (modelos.Vehiculo v : disponibles) {
            System.out.println(v);
        }

        List<Integer> idsElegidos = new ArrayList<>();
        boolean seguirAgregando = true;
        while (seguirAgregando) {
            System.out.print("ID de vehículo a agregar: ");
            int idVehiculo = Integer.parseInt(scanner.nextLine());
            idsElegidos.add(idVehiculo);

            System.out.print("¿Agregar otro vehículo? (s/n): ");
            String respuesta = scanner.nextLine();
            seguirAgregando = respuesta.equalsIgnoreCase("s");
        }

        System.out.print("DNI del cliente: ");
        String dniCliente = scanner.nextLine();

        System.out.println("Oficinas disponibles:");
        for (modelos.Oficina o : servicio.getOficinas()) {
            System.out.println(o);
        }
        System.out.print("ID de oficina destino: ");
        int idOficinaDestino = Integer.parseInt(scanner.nextLine());

        System.out.print("Litros de gasolina inicial: ");
        double litros = Double.parseDouble(scanner.nextLine());

        boolean exito = servicio.registrarNuevaReserva(dniCliente, idsElegidos, fechaInicio, fechaFin, idOficinaDestino, litros);

        if (exito) {
            System.out.println("Reserva registrada correctamente.");
        } else {
            System.out.println("Error: revisá el DNI del cliente, los vehículos elegidos o la oficina destino.");
        }
    }

    static void agregarCliente(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
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
            System.out.println("Reserva marcada como entregada. Los vehículos ya están disponibles en la oficina destino.");
        } else {
            System.out.println("Error: la reserva no existe o ya estaba entregada.");
        }
    }

    static void verMisReservas(servicios.SixtServicio servicio, dto.UsuarioDTO usuario) {
        dto.ClienteDTO cliente = (dto.ClienteDTO) usuario;
        List<modelos.Reserva> misReservas = servicio.getReservasPorCliente(cliente.getId());

        if (misReservas.isEmpty()) {
            System.out.println("No tenés reservas registradas.");
        } else {
            System.out.println("--- TUS RESERVAS ---");
            for (modelos.Reserva r : misReservas) {
                System.out.println(r);
            }
        }
    }

    static void verMiInfo(dto.UsuarioDTO usuario) {
        dto.ClienteDTO cliente = (dto.ClienteDTO) usuario;
        System.out.println("--- TU INFORMACIÓN ---");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Usuario: " + cliente.getUsername());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("DNI: " + cliente.getDni());
    }

    static void agregarUsuario(Scanner scanner, servicios.SixtServicio servicio) {
        System.out.println("1. Vendedor");
        System.out.println("2. Administrador");
        System.out.print("Tipo de usuario: ");
        int tipoOpcion = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
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
            System.out.println("No hay vehículos cargados.");
        } else {
            System.out.println("--- VEHÍCULOS ---");
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
            System.out.println("Fecha inválida. Usá el formato DD/MM/YYYY.");
            return;
        }

        List<modelos.Vehiculo> disponibles = servicio.getVehiculosDisponibles(fechaInicio, fechaFin);
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles para ese rango de fechas.");
        } else {
            System.out.println("--- VEHÍCULOS DISPONIBLES ---");
            for (modelos.Vehiculo v : disponibles) {
                System.out.println(v);
            }
        }
    }
}
