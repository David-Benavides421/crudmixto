package com.empresa.crudmixto.service;

import com.empresa.crudmixto.entity.Proyecto;
import com.empresa.crudmixto.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProyectoService {
    private final ProyectoRepository repo;

    public ProyectoService(ProyectoRepository repo) {
        this.repo = repo;
    }

    public List<Proyecto> listar() { return repo.findAll(); }

    public Proyecto guardar(Proyecto p) { return repo.save(p); }

    public Proyecto obtener(String id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado")); }

    public void eliminar(String id) { repo.deleteById(id); }

    public List<Proyecto> listarPorEmpleado(Long empleadoId) { return repo.findByEmpleadoId(empleadoId); }
}
