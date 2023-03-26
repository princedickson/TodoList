package com.security.TodoList.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class uUser {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Firstname;
    private String Lastname;
    private String Email;
    @Column(length = 60)
    private String Password;
    private String Roles;
    private boolean enable  = false;

}
