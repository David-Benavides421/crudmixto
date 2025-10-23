package com.empresa.crudmixto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "proyectos")
public class Proyecto {

    @Id
    private String id;

    @NotBlank(message = "Nombre del proyecto obligatorio")
    private String nombre;

    private String descripcion;

    // referencia al empleado en MySQL
    private Long empleadoId;

    // campos transient para mostrar datos del empleado
    @Transient
    private String nombreEmpleado;

    @Transient
    private String cargoEmpleado;

    // lista de tareas (IDs de documentos Tarea)
    private List<String> tareasIds = new ArrayList<>();

    // Constructors
    public Proyecto() {}
    public Proyecto(String nombre, String descripcion, Long empleadoId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleadoId = empleadoId;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public String getCargoEmpleado() { return cargoEmpleado; }
    public void setCargoEmpleado(String cargoEmpleado) { this.cargoEmpleado = cargoEmpleado; }

    public List<String> getTareasIds() { return tareasIds; }
    public void setTareasIds(List<String> tareasIds) { this.tareasIds = tareasIds; }
}
