package com.empresa.crudmixto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "tareas")
public class Tarea {

    @Id
    private String id;

    @NotBlank(message = "Título obligatorio")
    private String titulo;

    private String detalle;

    // referencia al proyecto
    private String proyectoId;

    public Tarea() {}
    public Tarea(String titulo, String detalle, String proyectoId) {
        this.titulo = titulo;
        this.detalle = detalle;
        this.proyectoId = proyectoId;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getProyectoId() { return proyectoId; }
    public void setProyectoId(String proyectoId) { this.proyectoId = proyectoId; }
}
