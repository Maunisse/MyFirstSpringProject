package com.example.springsecurityapplication.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_role")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    @OneToMany(mappedBy = "role_role")
    private List<Person> person;

    public Role() {
    }

    public Role(int id, String name, List<Person> person) {
        this.id = id;
        this.name = name;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }
}
