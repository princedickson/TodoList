package com.security.TodoList.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstname;
    private String lastname;
    private String email;
    @Column(length = 60)
    private String Password;
    private String Roles;
    private boolean enable  = false;

}
