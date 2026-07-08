/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

//herramienta para manejar fechas reales
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author
 *
 * Esta clase es una entidad transaccional que asocia, no almacena solo datos
 * propios sino que conecta clientes, vehiculos y oficina en un determinado
 * momento.
 */
public class Reserva {

    private String idReserva;
    private Cliente clienteTitular;
    // Usamos una lista por que se pueden reservar varios vehiculos a la ves
    private List<Vehiculo> vehiculosAlquilados;
    //son dos asociaciones a la clase Oficina. 
    //esto resuelve el requerimiento de que un auto pueda ser devuelto en una sucursal distinta a la de origen.
    private Oficina oficinaOrigen;
    private Oficina oficinaDestino;
    //para saber cuantos dias pasaron entre ambas fechas 
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double litrosGasolinaInicial;
    private double precioTotal;
    private boolean entregado;

    //Usamos una lista
    public Reserva(String idReserva, Cliente clienteTitular, List<Vehiculo> vehiculosAlquilados, Oficina oficinaOrigen, Oficina oficinaDestino, LocalDate fechaInicio, LocalDate fechaFin, double litrosGasolinaInicial, double precioTotal, boolean entregado) {
        this.idReserva = idReserva;
        this.clienteTitular = clienteTitular;
        this.vehiculosAlquilados = vehiculosAlquilados;
        this.oficinaOrigen = oficinaOrigen;
        this.oficinaDestino = oficinaDestino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.litrosGasolinaInicial = litrosGasolinaInicial;
        this.precioTotal = precioTotal;

        /**
         * *entrega no lo seteo con false por defecto por que java va a leer el
         * archivo y va a ignorar lo que dice y lo va a setear en false por
         * defecto. todas las reservas finalizadas van a vovler a estar activas
         * cada vez que reiniciamos el sistema.
        *
         */
        this.entregado = entregado;
    }

    // Getters
    public String getIdReserva() {
        return idReserva;
    }

    public Cliente getClienteTitular() {
        return clienteTitular;
    }

    public List<Vehiculo> getVehiculosAlquilados() {
        return vehiculosAlquilados;
    }

    public Oficina getOficinaOrigen() {
        return oficinaOrigen;
    }

    public Oficina getOficinaDestino() {
        return oficinaDestino;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getLitrosGasolinaInicial() {
        return litrosGasolinaInicial;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public boolean isEntregado() {
        return entregado;
    }

    //setters
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public void setClienteTitular(Cliente clienteTitular) {
        this.clienteTitular = clienteTitular;
    }

    public void setVehiculosAlquilados(List<Vehiculo> vehiculosAlquilados) {
        this.vehiculosAlquilados = vehiculosAlquilados;
    }

    public void setOficinaOrigen(Oficina oficinaOrigen) {
        this.oficinaOrigen = oficinaOrigen;
    }

    public void setOficinaDestino(Oficina oficinaDestino) {
        this.oficinaDestino = oficinaDestino;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setLitrosGasolinaInicial(double litrosGasolinaInicial) {
        this.litrosGasolinaInicial = litrosGasolinaInicial;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String toCSV() {
        //para no guardar el objeto entero en el txt, guardamos el DNI del cliente y la patente de los autos.
        // Unimos todas las patentes de la lista con una coma.
        //este for recorre la lista de vehículos
        //extraela patente de cada uno y las pega en una sola cadena de texto separadas por comas.
        StringBuilder patentes = new StringBuilder();
        for (int i = 0; i < vehiculosAlquilados.size(); i++) {
            patentes.append(vehiculosAlquilados.get(i).getPatente());
            if (i < vehiculosAlquilados.size() - 1) {
                patentes.append(","); // Separa patentes con coma
            }
        }

        return idReserva + ";"
                + clienteTitular.getDni() + ";"
                + patentes.toString() + ";"
                + oficinaOrigen.getIdOficina() + ";"
                + oficinaDestino.getIdOficina() + ";"
                + fechaInicio.toString() + ";"
                + fechaFin.toString() + ";"
                + litrosGasolinaInicial + ";"
                + precioTotal + ";"
                + entregado;
    }

    //sobreescribimos toString asi se vera una linea de reservas.txt
    @Override
    public String toString() {
        String estado = entregado ? "FINALIZADA" : "EN CURSO";
        return "Reserva #" + idReserva + " | Cliente: " + clienteTitular.getNombre() + " | Estado: " + estado;
    }
}
