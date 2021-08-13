package esportapplication.code.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter @Setter
@Entity
@Table(name = "`GROUPS`")
public class Group implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(
            name="USERS_GROUPS",
            joinColumns = @JoinColumn( name="GROUP_ID"),
            inverseJoinColumns = @JoinColumn( name="USER_ID")
    )
    List<User> users;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(
            name="GROUPS_PRIVILEGES",
            joinColumns = @JoinColumn( name="GROUP_ID"),
            inverseJoinColumns = @JoinColumn( name="PRIVILEGE_ID")
    )
    List<Privilege> privileges;
}
