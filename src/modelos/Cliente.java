package modelos;

public class Cliente extends Usuario {

    private String dni;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    public Cliente(int id, String username, String password, String dni, String nombre, String direccion, String email, String telefono) {
        super(id, username, password);
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters
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

    // Setters
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

    @Override
    public String getRol() {
        return "CLIENTE";
    }

    @Override
    public String toCSV() {
        // Formato: id;ROL;user;pass;dni;nombre;direccion;email;telefono
        return id + ";" + getRol() + ";" + username + ";" + password + ";" + dni + ";" + nombre + ";" + direccion + ";" + email + ";" + telefono;
    }

    @Override
    public String toString() {
        return "Cliente [" + id + "] " + nombre + " | DNI: " + dni;
    }
}