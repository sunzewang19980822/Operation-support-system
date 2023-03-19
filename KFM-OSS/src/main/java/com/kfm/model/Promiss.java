package com.kfm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Promiss implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String icon;
    private String path;
    @ManyToOne
    @JsonIgnore
    private Promiss parent;
    @OneToMany(mappedBy = "parent")
//    @JsonIgnore
    private Set<Promiss> children;

    public Promiss(Integer id) {
        this.id = id;
    }

    public Promiss(String name, String icon, String path, Promiss parent) {
        this.name = name;
        this.icon = icon;
        this.path = path;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promiss promiss = (Promiss) o;
        return id.equals(promiss.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Promiss{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
