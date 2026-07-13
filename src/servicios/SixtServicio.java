/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import dao.SixtDAO;
import dto.UsuarioDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelos.Administrador;
import modelos.Usuario;
import modelos.Oficina;
import modelos.Vehiculo;
import modelos.Reserva;
import modelos.Auto;
import modelos.Camioneta;
import modelos.Cliente;
import modelos.Vendedor;

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
    private List<Oficina> oficinas;
    private List<Vehiculo> vehiculos;
    private List<Reserva> reservas;

    public SixtServicio() {
        this.dao = new SixtDAO();
        this.AuthServicios = new AuthServicios();

        // para cargar el txt en la memoria
        cargarDatosEnMemoria();
    }

    //aca esta el metodo que carga desarrollado
    private void cargarDatosEnMemoria() {
        this.oficinas = dao.leerOficinas();

        this.usuarios = dao.leerUsuarios();
        if (this.usuarios.isEmpty()) {
            int idAdmin = dao.siguienteIdUsuario(this.usuarios);
            modelos.Administrador adminRoot = new modelos.Administrador(idAdmin, "admin", "1234");
            this.usuarios.add(adminRoot);
            dao.guardarUsuarios(this.usuarios);
            System.out.println("Sistema inicializado: Se ha creado el usuario 'admin' con clave '1234'.");
        }

        this.vehiculos = dao.leerVehiculos(oficinas);
        this.reservas = dao.leerReservas(usuarios, vehiculos, oficinas);
    }

    //metodo para iniciar sesion en el main
    public UsuarioDTO iniciarSesion(String username, String password) {
        return AuthServicios.login(username, password, this.usuarios);
    }

    //metodo para registrar un nuevo cliente
    public boolean registrarNuevoCliente(String username, String password, String dni, String nombre, String direccion, String email, String telefono) {
        // Verificar que no exista el mismo username
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        // Verificar que no exista el mismo DNI
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c && c.getDni().equals(dni)) {
                return false;
            }
        }

        int id = dao.siguienteIdUsuario(this.usuarios);
        Cliente nuevoCliente = new Cliente(id, username, password, dni, nombre, direccion, email, telefono);

        this.usuarios.add(nuevoCliente);
        dao.guardarUsuarios(this.usuarios);
        return true;
    }

    public boolean registrarNuevaOficina(String nombre, String direccion) {
        // Verificamos que no exista ya una oficina con ese nombre (sin importar mayúsc/minúsc)
        for (Oficina o : oficinas) {
            if (o.getNombre().equalsIgnoreCase(nombre)) {
                return false; // Ya existe una oficina con ese nombre
            }
        }

        int id = dao.siguienteIdOficina(this.oficinas);
        Oficina nuevaOficina = new Oficina(id, nombre, direccion);

        this.oficinas.add(nuevaOficina);
        dao.guardarOficinas(this.oficinas);
        return true;
    }

    public boolean registrarNuevoVehiculo(String tipo, String patente, String marca, String modelo, String color, double precioBaseDiario, int idOficina, double recargoCapacidad) {
        // Validar que exista al menos una oficina
        if (oficinas.isEmpty()) {
            return false;
        }

        // Buscar la oficina por id
        Oficina oficinaElegida = null;
        for (Oficina o : oficinas) {
            if (o.getIdOficina() == idOficina) {
                oficinaElegida = o;
                break;
            }
        }
        if (oficinaElegida == null) {
            return false;
        }

        // Validar que la patente no esté repetida
        for (Vehiculo v : vehiculos) {
            if (v.getPatente().equalsIgnoreCase(patente)) {
                return false;
            }
        }

        int id = dao.siguienteIdVehiculo(this.vehiculos);
        Vehiculo nuevoVehiculo;

        if (tipo.equals("AUTO")) {
            nuevoVehiculo = new Auto(id, patente, marca, modelo, color, precioBaseDiario, oficinaElegida);
        } else {
            nuevoVehiculo = new Camioneta(id, patente, marca, modelo, color, precioBaseDiario, oficinaElegida, recargoCapacidad);
        }

        this.vehiculos.add(nuevoVehiculo);
        dao.guardarVehiculos(this.vehiculos);
        return true;
    }

    public List<Oficina> getOficinas() {
        return oficinas;
    }

    public List<Vehiculo> getVehiculosDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Vehiculo> disponibles = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            boolean ocupado = false;

            for (Reserva r : reservas) {
                if (r.getVehiculosAlquilados().contains(v)) {
                    boolean seSolapan = !(fechaInicio.isAfter(r.getFechaFin()) || fechaFin.isBefore(r.getFechaInicio()));
                    if (seSolapan) {
                        ocupado = true;
                        break;
                    }
                }
            }

            if (!ocupado) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public boolean registrarNuevaReserva(String dniCliente, List<Integer> idsVehiculos, LocalDate fechaInicio, LocalDate fechaFin, int idOficinaDestino, double litrosGasolinaInicial) {

        // Buscar cliente por DNI
        Cliente cliente = null;
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c && c.getDni().equals(dniCliente)) {
                cliente = c;
                break;
            }
        }
        if (cliente == null) {
            return false;
        }

        // Buscar oficina destino
        Oficina oficinaDestino = null;
        for (Oficina o : oficinas) {
            if (o.getIdOficina() == idOficinaDestino) {
                oficinaDestino = o;
                break;
            }
        }
        if (oficinaDestino == null) {
            return false;
        }

        // Buscar los vehículos elegidos y validar que sigan disponibles
        List<Vehiculo> disponibles = getVehiculosDisponibles(fechaInicio, fechaFin);
        List<Vehiculo> vehiculosElegidos = new ArrayList<>();

        for (int idVehiculo : idsVehiculos) {
            Vehiculo encontrado = null;
            for (Vehiculo v : disponibles) {
                if (v.getId() == idVehiculo) {
                    encontrado = v;
                    break;
                }
            }
            if (encontrado == null) {
                return false; // El vehículo no existe o ya no está disponible
            }
            vehiculosElegidos.add(encontrado);
        }

        if (vehiculosElegidos.isEmpty()) {
            return false;
        }

        // La oficina de origen es la del primer vehículo elegido
        Oficina oficinaOrigen = vehiculosElegidos.get(0).getOficinaActual();

        // Calcular precio total
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        double precioTotal = 0;
        for (Vehiculo v : vehiculosElegidos) {
            precioTotal += v.calcularAlquiler((int) dias);
        }

        int id = dao.siguienteIdReserva(this.reservas);
        Reserva nuevaReserva = new Reserva(id, cliente, vehiculosElegidos, oficinaOrigen, oficinaDestino,
                fechaInicio, fechaFin, litrosGasolinaInicial, precioTotal, false);

        this.reservas.add(nuevaReserva);
        dao.guardarReservas(this.reservas);
        return true;
    }

    public boolean marcarReservaComoEntregada(int idReserva) {
        Reserva reservaEncontrada = null;
        for (Reserva r : reservas) {
            if (r.getIdReserva() == idReserva) {
                reservaEncontrada = r;
                break;
            }
        }

        if (reservaEncontrada == null || reservaEncontrada.isEntregado()) {
            return false; // No existe, o ya estaba entregada
        }

        reservaEncontrada.setEntregado(true);

        // Actualizamos la ubicación actual de cada vehículo a la oficina destino
        for (Vehiculo v : reservaEncontrada.getVehiculosAlquilados()) {
            v.setOficinaActual(reservaEncontrada.getOficinaDestino());
        }

        dao.guardarReservas(this.reservas);
        dao.guardarVehiculos(this.vehiculos); // persistimos la nueva ubicación de los vehículos
        return true;
    }

    public List<Reserva> getReservasPendientes() {
        List<Reserva> pendientes = new ArrayList<>();
        for (Reserva r : reservas) {
            if (!r.isEntregado()) {
                pendientes.add(r);
            }
        }
        return pendientes;
    }

    public List<Reserva> getReservasPorCliente(int idCliente) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getClienteTitular().getId() == idCliente) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public boolean registrarNuevoVendedor(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }

        int id = dao.siguienteIdUsuario(this.usuarios);
        Vendedor nuevoVendedor = new Vendedor(id, username, password);

        this.usuarios.add(nuevoVendedor);
        dao.guardarUsuarios(this.usuarios);
        return true;
    }

    public boolean registrarNuevoAdministrador(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }

        int id = dao.siguienteIdUsuario(this.usuarios);
        Administrador nuevoAdmin = new Administrador(id, username, password);

        this.usuarios.add(nuevoAdmin);
        dao.guardarUsuarios(this.usuarios);
        return true;
    }
    public List<Vehiculo> getVehiculos() {
    return vehiculos;
}

public List<Reserva> getReservas() {
    return reservas;
}
}
