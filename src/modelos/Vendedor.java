package modelos;

public class Vendedor extends Usuario {

    public Vendedor(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String getRol() {
        return "VENDEDOR";
    }

    @Override
    public String toCSV() {
        // Formato: id;ROL;user;pass
        return id + ";" + getRol() + ";" + username + ";" + password;
    }

    @Override
    public String toString() {
        return "Vendedor [" + username + "]";
    }
}