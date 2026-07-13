package modelos;

public class Administrador extends Usuario {

    public Administrador(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String getRol() {
        return "ADMIN";
    }

    @Override
    public String toCSV() {
        return id + ";" + getRol() + ";" + username + ";" + password;
    }

    @Override
    public String toString() {
        return "Administrador [" + username + "]";
    }
}