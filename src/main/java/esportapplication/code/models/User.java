package esportapplication.code.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity @Table(name="Users")
public class User implements Serializable {
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;
    @NotNull @Column (name = "username", unique = true)
    private String username;
    @NotNull @Column (name= "password")
    private String password;

    @Column(name = "first_name")
    private String name;

    @Column(name = "surname")
    private String surname;


    @NotNull @Email @Column(name = "email",unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name="USERS_GROUPS",
            joinColumns = @JoinColumn( name="USER_ID"),
            inverseJoinColumns = @JoinColumn( name="GROUP_ID")
    )
    List<Group> groups;

    @Transient
    List<Privilege> privileges;

}
