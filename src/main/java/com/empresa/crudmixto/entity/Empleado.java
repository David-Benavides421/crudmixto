package com.empresa.crudmixto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empleados",
       uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "Cargo obligatorio")
    @Size(max = 100)
    private String cargo;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email inválido")
    @Size(max = 150)
    @Column(unique = true, nullable = false)
    private String email;

    // Constructors
    public Empleado() {}
    public Empleado(String nombre, String cargo, String email) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.email = email;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
