package com.empresa.crudmixto.service;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.entity.Proyecto;
import com.empresa.crudmixto.repository.EmpleadoRepository;
import com.empresa.crudmixto.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final EmpleadoRepository empleadoRepository;
    private final ProyectoRepository proyectoRepository;

    public ReporteService(EmpleadoRepository empleadoRepository, ProyectoRepository proyectoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.proyectoRepository = proyectoRepository;
    }

    // Reporte combinado: Empleados con sus proyectos
    public List<Map<String, Object>> getEmpleadosConProyectos() {
        List<Empleado> empleados = empleadoRepository.findAll();
        List<Proyecto> proyectos = proyectoRepository.findAll();

        // Agrupar proyectos por empleadoId
        Map<Long, List<Proyecto>> proyectosPorEmpleado = proyectos.stream()
                .filter(p -> p.getEmpleadoId() != null)
                .collect(Collectors.groupingBy(Proyecto::getEmpleadoId));

        return empleados.stream().map(empleado -> {
            List<Proyecto> proyectosDelEmpleado = proyectosPorEmpleado.getOrDefault(empleado.getId(), List.of());
            return Map.of(
                    "empleado", empleado,
                    "proyectos", proyectosDelEmpleado
            );
        }).collect(Collectors.toList());
    }

    // Reporte de proyectos con información del empleado
    public List<Map<String, Object>> getProyectosConEmpleados() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();

        // Mapa de empleados por ID
        Map<Long, Empleado> empleadosMap = empleados.stream()
                .collect(Collectors.toMap(Empleado::getId, e -> e));

        return proyectos.stream().map(proyecto -> {
            Empleado empleado = empleadosMap.get(proyecto.getEmpleadoId());
            return Map.of(
                    "proyecto", proyecto,
                    "empleado", empleado
            );
        }).collect(Collectors.toList());
    }
}