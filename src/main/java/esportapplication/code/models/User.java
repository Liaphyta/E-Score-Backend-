package esportapplication.code.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity @Table(name="Users")
public class User {
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    long id;
    @NotNull @Column (name = "username", unique = true)
    private String username;
    @NotNull @Column (name= "password")
    private String password;

    @NotNull @Email @Column(name = "email",unique = true)
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
