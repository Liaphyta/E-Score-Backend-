package esportapplication.code.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "APP_PRIVILEGES")
public class Privilege  implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "NAME")
    String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="GROUPS_PRIVILEGES",
            joinColumns = @JoinColumn( name="PRIVILEGE_ID"),
            inverseJoinColumns = @JoinColumn( name="GROUP_ID")
    )
    List<Group> groups;
}
