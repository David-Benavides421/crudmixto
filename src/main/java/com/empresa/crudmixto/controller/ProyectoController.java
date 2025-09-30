package com.empresa.crudmixto.controller;

import com.empresa.crudmixto.entity.Proyecto;
import com.empresa.crudmixto.entity.Tarea;
import com.empresa.crudmixto.service.ProyectoService;
import com.empresa.crudmixto.service.EmpleadoService;
import com.empresa.crudmixto.service.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final EmpleadoService empleadoService;
    private final TareaService tareaService;

    public ProyectoController(ProyectoService proyectoService, EmpleadoService empleadoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.empleadoService = empleadoService;
        this.tareaService = tareaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proyectos", proyectoService.listar());
        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("empleados", empleadoService.listar());
        return "proyectos";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Proyecto proyecto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("proyectos", proyectoService.listar());
            model.addAttribute("empleados", empleadoService.listar());
            return "proyectos";
        }
        proyectoService.guardar(proyecto);
        return "redirect:/proyectos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        proyectoService.eliminar(id);
        return "redirect:/proyectos";
    }

    @GetMapping("/{id}")
    public String verProyecto(@PathVariable String id, Model model) {
        Proyecto p = proyectoService.obtener(id);
        model.addAttribute("proyecto", p);
        model.addAttribute("tareas", tareaService.listarPorProyecto(id));
        model.addAttribute("tarea", new Tarea());
        return "proyecto-detalle";
    }

    // Agregar tarea al proyecto (delegado al TareaService)
    @PostMapping("/{id}/tareas/guardar")
    public String guardarTarea(@PathVariable String id, @Valid @ModelAttribute Tarea tarea, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "redirect:/proyectos/" + id;
        }
        tarea.setProyectoId(id);
        tareaService.guardar(tarea);
        return "redirect:/proyectos/" + id;
    }

    @GetMapping("/{proyectoId}/tareas/eliminar/{tareaId}")
    public String eliminarTarea(@PathVariable String proyectoId, @PathVariable String tareaId) {
        tareaService.eliminar(tareaId);
        return "redirect:/proyectos/" + proyectoId;
    }
}
