package a2.A2.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
@Entity
public class Franchise {
    @TableGenerator(
            name = "franchiseSeq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(
            strategy=GenerationType.TABLE,
            generator="franchiseSeq")
    @Id
    private long id;

    @OneToMany(targetEntity = Movie.class)
    @JoinColumn(name = "franchise")
    private List<Movie> movies;

    @Column(unique = true)
    private String name;

    static long lastID = 1;

    public Franchise(){}


    public Franchise(String name) {
        this.name = name;

        this.id = lastID;
        lastID += 1;
    }

    public Franchise(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Franchise franchise = (Franchise) o;
        return getId() == franchise.getId() && Objects.equals(getName(), franchise.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return name;
    }

}

