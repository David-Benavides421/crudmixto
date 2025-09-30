package com.empresa.crudmixto.controller;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.service.EmpleadoService;
import com.empresa.crudmixto.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final ProyectoService proyectoService;

    public EmpleadoController(EmpleadoService empleadoService, ProyectoService proyectoService) {
        this.empleadoService = empleadoService;
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listar(@RequestParam(value = "q", required = false) String q, Model model) {
        if (q == null || q.isBlank()) {
            model.addAttribute("empleados", empleadoService.listar());
        } else {
            model.addAttribute("empleados", empleadoService.buscar(q));
            model.addAttribute("q", q);
        }
        model.addAttribute("empleado", new Empleado());
        return "empleados";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Empleado empleado, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("empleados", empleadoService.listar());
            return "empleados";
        }
        try {
            empleadoService.guardar(empleado);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("empleados", empleadoService.listar());
            return "empleados";
        }
        return "redirect:/empleados";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return "redirect:/empleados";
    }

    @GetMapping("/{id}/proyectos")
    public String proyectosEmpleado(@PathVariable Long id, Model model) {
        model.addAttribute("empleado", empleadoService.obtener(id));
        model.addAttribute("proyectos", proyectoService.listarPorEmpleado(id));
        return "proyectos-por-empleado";
    }
}
