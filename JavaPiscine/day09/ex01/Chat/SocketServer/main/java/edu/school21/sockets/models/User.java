package edu.school21.sockets.models;

public class User {
    private Long id;
    private String password;
    private String name;

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", password='" + password + "'" +
            ", name='" + name + "'" +
            "}";
    }
    
}
