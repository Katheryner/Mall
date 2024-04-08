package entity;

public class Client {

    private int id_client;
    private String name;
    private String last_name;
    private String email;

    public Client() {

    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Client(int id_client, String name, String last_name, String email) {
        this.id_client = id_client;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client : " +
                "id_client=" + id_client +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
