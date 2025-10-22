package com.empresa.crudmixto.controller;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.service.EmpleadoService;
import com.empresa.crudmixto.service.ExportService;
import com.empresa.crudmixto.service.ProyectoService;
import com.empresa.crudmixto.service.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final ProyectoService proyectoService;
    private final ExportService exportService;
    private final ReporteService reporteService;

    public EmpleadoController(EmpleadoService empleadoService, ProyectoService proyectoService,
                              ExportService exportService, ReporteService reporteService) {
        this.empleadoService = empleadoService;
        this.proyectoService = proyectoService;
        this.exportService = exportService;
        this.reporteService = reporteService;
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

    @GetMapping("/reportes")
    public String reportes(Model model) {
        model.addAttribute("empleadosConProyectos", reporteService.getEmpleadosConProyectos());
        model.addAttribute("proyectosConEmpleados", reporteService.getProyectosConEmpleados());
        return "reportes";
    }

    @GetMapping("/export/empleados/excel")
    public ResponseEntity<byte[]> exportEmpleadosExcel() throws IOException {
        List<Empleado> empleados = empleadoService.listar();
        byte[] excelData = exportService.exportEmpleadosToExcel(empleados);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "empleados.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }

    @GetMapping("/export/empleados/pdf")
    public ResponseEntity<byte[]> exportEmpleadosPDF() throws IOException {
        List<Empleado> empleados = empleadoService.listar();
        byte[] pdfData = exportService.exportEmpleadosToPDF(empleados);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "empleados.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }
}
