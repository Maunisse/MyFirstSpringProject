package com.example.springsecurityapplication.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.ConnectionBuilder;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Логин не может быть пустым")
//    @Size(min = 5, max = 100, message = "Паро должен быть от 5 до 100 символов")
    @Column(name = "password")
//    @Max(value =100, message = "Пароль не может быть больше 100 символов")
    @Pattern(regexp = "(?=^.{8,100}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, специальный символ, букву в верхнем и нижнем регистрах")
    private String password;

    @ManyToOne(optional = false)
    private Role role_role;

    public Role getRole_role() {
        return role_role;
    }

    public void setRole_role(Role role_role) {
        this.role_role = role_role;
    }

    //    @Column(name = "role")
//    private String role;
//
//    public Person(String role) {
//        this.role = role;
//    }

    //Так как связь многие ко многим реализуется через третью смежную таблицу, то указываем ее и ее поля
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns=@JoinColumn(name = "product_id"))
    private List<Product> product;

    @OneToMany(mappedBy = "person")
    private List<Order> orderList;

//    public Person() {
//
//
//    }
//
//    public Person(int id, String login, String password) {
//        this.id = id;
//        this.login = login;
//        this.password = password;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public void setPassword(String password) {
        this.password = password;
    }
}
