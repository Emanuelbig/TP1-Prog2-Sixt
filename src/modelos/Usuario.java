package modelos;

public abstract class Usuario {

    protected int id;
    protected String username;
    protected String password;

    public Usuario(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Nota: no hay setId() a propósito. El id se define una sola vez al crear
    // el objeto y no debería cambiar nunca (mismo criterio que una PK en una tabla).

    public abstract String getRol();
    public abstract String toCSV();
}