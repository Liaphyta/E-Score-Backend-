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
@Entity
@Table(name="head2head")
public class Head2Head implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    @Column (name = "first_opponent")
    private String firstOpponent;
    @NotNull
    @Column(name= "second_opponent")
    private String secondOpponent;
    @Column(name = "league_name")
    private String leagueName;
    @Column(name = "score_first")
    private Integer scoreFirst;
    @Column(name = "score_second")
    private Integer scoreSecond;

}