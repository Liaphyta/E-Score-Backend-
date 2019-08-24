package esportapplication.code.models;

import javax.persistence.*;

@Entity @Table(name="Teams")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="imgSource")
    private String imgSource;

    @Column(name="foundedSince")
    private String foundedSince;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public String getFoundedSince() {
        return foundedSince;
    }

    public void setFoundedSince(String foundedSince) {
        this.foundedSince = foundedSince;
    }
}
