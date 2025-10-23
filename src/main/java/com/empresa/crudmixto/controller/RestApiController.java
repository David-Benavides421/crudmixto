package com.empresa.crudmixto.controller;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.entity.Proyecto;
import com.empresa.crudmixto.service.EmpleadoService;
import com.empresa.crudmixto.service.ProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class RestApiController {

    private final EmpleadoService empleadoService;
    private final ProyectoService proyectoService;

    public RestApiController(EmpleadoService empleadoService, ProyectoService proyectoService) {
        this.empleadoService = empleadoService;
        this.proyectoService = proyectoService;
    }

    // === EMPLEADOS API ===

    /**
     * GET /api/empleados
     * Obtiene todos los empleados
     */
    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.listar();
        return ResponseEntity.ok(empleados);
    }

    /**
     * GET /api/empleados/{id}
     * Obtiene un empleado por ID
     */
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.obtener(id);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/empleados
     * Crea un nuevo empleado
     */
    @PostMapping("/empleados")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado savedEmpleado = empleadoService.guardar(empleado);
            return ResponseEntity.ok(savedEmpleado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/empleados/{id}
     * Actualiza un empleado existente
     */
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            Empleado updatedEmpleado = empleadoService.guardar(empleado);
            return ResponseEntity.ok(updatedEmpleado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/empleados/{id}
     * Elimina un empleado por ID
     */
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/empleados/search?q={query}
     * Busca empleados por nombre o cargo
     */
    @GetMapping("/empleados/search")
    public ResponseEntity<List<Empleado>> searchEmpleados(@RequestParam String q) {
        List<Empleado> empleados = empleadoService.buscar(q);
        return ResponseEntity.ok(empleados);
    }

    // === PROYECTOS API ===

    /**
     * GET /api/proyectos
     * Obtiene todos los proyectos
     */
    @GetMapping("/proyectos")
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        List<Proyecto> proyectos = proyectoService.listar();
        return ResponseEntity.ok(proyectos);
    }

    /**
     * GET /api/proyectos/{id}
     * Obtiene un proyecto por ID
     */
    @GetMapping("/proyectos/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable String id) {
        try {
            Proyecto proyecto = proyectoService.obtener(id);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/proyectos
     * Crea un nuevo proyecto
     */
    @PostMapping("/proyectos")
    public ResponseEntity<Proyecto> createProyecto(@RequestBody Proyecto proyecto) {
        try {
            Proyecto savedProyecto = proyectoService.guardar(proyecto);
            return ResponseEntity.ok(savedProyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/proyectos/{id}
     * Actualiza un proyecto existente
     */
    @PutMapping("/proyectos/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable String id, @RequestBody Proyecto proyecto) {
        try {
            proyecto.setId(id);
            Proyecto updatedProyecto = proyectoService.guardar(proyecto);
            return ResponseEntity.ok(updatedProyecto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/proyectos/{id}
     * Elimina un proyecto por ID
     */
    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable String id) {
        try {
            proyectoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/proyectos/empleado/{empleadoId}
     * Obtiene proyectos por empleado ID
     */
    @GetMapping("/proyectos/empleado/{empleadoId}")
    public ResponseEntity<List<Proyecto>> getProyectosByEmpleado(@PathVariable Long empleadoId) {
        List<Proyecto> proyectos = proyectoService.listarPorEmpleado(empleadoId);
        return ResponseEntity.ok(proyectos);
    }
}