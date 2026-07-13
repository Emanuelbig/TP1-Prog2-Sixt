package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelos.*;


/**
*
* * Grupo H, Programacion 2, Turno Noche, Año 2026
* 
**/

public class SixtDAO {

    private final Path RUTA_USUARIOS = Paths.get("data/usuarios.txt");
    private final Path RUTA_VEHICULOS = Paths.get("data/vehiculos.txt");
    private final Path RUTA_RESERVAS = Paths.get("data/reservas.txt");
    private final Path RUTA_OFICINAS = Paths.get("data/oficinas.txt");

    // ================= SECCIÓN USUARIOS =================

    public void guardarUsuarios(List<Usuario> usuarios) {
        List<String> lineas = new ArrayList<>();
        for (Usuario u : usuarios) {
            lineas.add(u.toCSV());
        }
        try {
            Files.write(RUTA_USUARIOS, lineas);
        } catch (IOException e) {
            System.out.println("Error critico al guardar los usuarios: " + e.getMessage());
        }
    }

    public List<Usuario> leerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        if (!Files.exists(RUTA_USUARIOS)) {
            return usuarios;
        }
        try {
            List<String> lineas = Files.readAllLines(RUTA_USUARIOS);
            for (String linea : lineas) {
                // Formato: id;ROL;user;pass;[codigo;dni;nombre;direccion;email;telefono]
                String[] datos = linea.split(",");

                int id = Integer.parseInt(datos[0]);
                String rol = datos[1];
                String username = datos[2];
                String password = datos[3];

                if (rol.equals("ADMIN")) {
                    usuarios.add(new Administrador(id, username, password));

                } else if (rol.equals("VENDEDOR")) {
                    usuarios.add(new Vendedor(id, username, password));

                } else if (rol.equals("CLIENTE")) {
                    String dni = datos[4];
                    String nombre = datos[5];
                    String direccion = datos[6];
                    String email = datos[7];
                    String telefono = datos[8];

                    usuarios.add(new Cliente(id, username, password, dni, nombre, direccion, email, telefono));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public int siguienteIdUsuario(List<Usuario> usuarios) {
        int max = 0;
        for (Usuario u : usuarios) {
            if (u.getId() > max) max = u.getId();
        }
        return max + 1;
    }

    // ================= SECCIÓN OFICINAS =================

    public void guardarOficinas(List<Oficina> oficinas) {
        List<String> lineas = new ArrayList<>();
        for (Oficina o : oficinas) {
            lineas.add(o.toCSV());
        }
        try {
            Files.write(RUTA_OFICINAS, lineas);
        } catch (IOException e) {
            System.out.println("Error al guardar oficinas: " + e.getMessage());
        }
    }

    public List<Oficina> leerOficinas() {
        List<Oficina> oficinas = new ArrayList<>();
        if (!Files.exists(RUTA_OFICINAS)) {
            return oficinas;
        }
        try {
            List<String> lineas = Files.readAllLines(RUTA_OFICINAS);
            for (String linea : lineas) {
                // Formato: id;nombre;direccion
                String[] d = linea.split(",");
                int id = Integer.parseInt(d[0]);
                oficinas.add(new Oficina(id, d[1], d[2]));
            }
        } catch (IOException e) {
            System.out.println("Error al leer oficinas: " + e.getMessage());
        }
        return oficinas;
    }

    public int siguienteIdOficina(List<Oficina> oficinas) {
        int max = 0;
        for (Oficina o : oficinas) {
            if (o.getIdOficina() > max) max = o.getIdOficina();
        }
        return max + 1;
    }

    // ================= SECCIÓN VEHÍCULOS =================
    // Necesita la lista de oficinas ya cargada, para reconstruir oficinaActual

    public void guardarVehiculos(List<Vehiculo> vehiculos) {
        List<String> lineas = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            lineas.add(v.toCSV());
        }
        try {
            Files.write(RUTA_VEHICULOS, lineas);
        } catch (IOException e) {
            System.out.println("Error al guardar vehiculos: " + e.getMessage());
        }
    }

    public List<Vehiculo> leerVehiculos(List<Oficina> oficinas) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        if (!Files.exists(RUTA_VEHICULOS)) {
            return vehiculos;
        }
        try {
            List<String> lineas = Files.readAllLines(RUTA_VEHICULOS);
            for (String linea : lineas) {
                // Formato: id;TIPO;patente;marca;modelo;color;precio;idOficina;[recargo]
                String[] d = linea.split(",");

                int id = Integer.parseInt(d[0]);
                String tipo = d[1];
                String patente = d[2];
                String marca = d[3];
                String modelo = d[4];
                String color = d[5];
                double precioBaseDiario = Double.parseDouble(d[6]);
                int idOficina = Integer.parseInt(d[7]);
                Oficina oficina = buscarOficinaPorId(oficinas, idOficina);

                if (tipo.equals("AUTO")) {
                    vehiculos.add(new Auto(id, patente, marca, modelo, color, precioBaseDiario, oficina));
                } else if (tipo.equals("CAMIONETA")) {
                    double recargoCapacidad = Double.parseDouble(d[8]);
                    vehiculos.add(new Camioneta(id, patente, marca, modelo, color, precioBaseDiario, oficina, recargoCapacidad));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer vehiculos: " + e.getMessage());
        }
        return vehiculos;
    }

    public int siguienteIdVehiculo(List<Vehiculo> vehiculos) {
        int max = 0;
        for (Vehiculo v : vehiculos) {
            if (v.getId() > max) max = v.getId();
        }
        return max + 1;
    }

    // ================= SECCIÓN RESERVAS =================
    // Necesita usuarios, vehículos y oficinas ya cargados, para reconstruir referencias

    public void guardarReservas(List<Reserva> reservas) {
        List<String> lineas = new ArrayList<>();
        for (Reserva r : reservas) {
            lineas.add(r.toCSV());
        }
        try {
            Files.write(RUTA_RESERVAS, lineas);
        } catch (IOException e) {
            System.out.println("Error al guardar reservas: " + e.getMessage());
        }
    }

    public List<Reserva> leerReservas(List<Usuario> usuarios, List<Vehiculo> vehiculos, List<Oficina> oficinas) {
        List<Reserva> reservas = new ArrayList<>();
        if (!Files.exists(RUTA_RESERVAS)) {
            return reservas;
        }
        try {
            List<String> lineas = Files.readAllLines(RUTA_RESERVAS);
            for (String linea : lineas) {
                // Formato: id;idCliente;idsVehiculos;idOficinaOrigen;idOficinaDestino;fechaInicio;fechaFin;litros;precioTotal;entregado
                String[] d = linea.split(",");

                int idReserva = Integer.parseInt(d[0]);
                int idCliente = Integer.parseInt(d[1]);
                Cliente cliente = buscarClientePorId(usuarios, idCliente);

                List<Vehiculo> vehiculosReserva = new ArrayList<>();
                for (String idTexto : d[2].split(",")) {
                    Vehiculo v = buscarVehiculoPorId(vehiculos, Integer.parseInt(idTexto));
                    if (v != null) vehiculosReserva.add(v);
                }

                int idOficinaOrigen = Integer.parseInt(d[3]);
                int idOficinaDestino = Integer.parseInt(d[4]);
                Oficina oOrigen = buscarOficinaPorId(oficinas, idOficinaOrigen);
                Oficina oDestino = buscarOficinaPorId(oficinas, idOficinaDestino);

                LocalDate fechaInicio = LocalDate.parse(d[5]);
                LocalDate fechaFin = LocalDate.parse(d[6]);
                double litros = Double.parseDouble(d[7]);
                double precioTotal = Double.parseDouble(d[8]);
                boolean entregado = Boolean.parseBoolean(d[9]);

                reservas.add(new Reserva(idReserva, cliente, vehiculosReserva, oOrigen, oDestino,
                        fechaInicio, fechaFin, litros, precioTotal, entregado));
            }
        } catch (IOException e) {
            System.out.println("Error al leer reservas: " + e.getMessage());
        }
        return reservas;
    }

    public int siguienteIdReserva(List<Reserva> reservas) {
        int max = 0;
        for (Reserva r : reservas) {
            if (r.getIdReserva() > max) max = r.getIdReserva();
        }
        return max + 1;
    }

    // ================= MÉTODOS AUXILIARES DE BÚSQUEDA =================

    private Oficina buscarOficinaPorId(List<Oficina> oficinas, int id) {
        for (Oficina o : oficinas) {
            if (o.getIdOficina() == id) return o;
        }
        return null;
    }

    private Vehiculo buscarVehiculoPorId(List<Vehiculo> vehiculos, int id) {
        for (Vehiculo v : vehiculos) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    private Cliente buscarClientePorId(List<Usuario> usuarios, int id) {
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c && c.getId() == id) return c;
        }
        return null;
    }
}