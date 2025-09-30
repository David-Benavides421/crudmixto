package com.empresa.crudmixto.service;

import com.empresa.crudmixto.entity.Tarea;
import com.empresa.crudmixto.entity.Proyecto;
import com.empresa.crudmixto.repository.TareaRepository;
import com.empresa.crudmixto.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TareaService {
    private final TareaRepository tareaRepo;
    private final ProyectoRepository proyectoRepo;

    public TareaService(TareaRepository tareaRepo, ProyectoRepository proyectoRepo) {
        this.tareaRepo = tareaRepo;
        this.proyectoRepo = proyectoRepo;
    }

    public List<Tarea> listarPorProyecto(String proyectoId) {
        return tareaRepo.findByProyectoId(proyectoId);
    }

    public Tarea guardar(Tarea t) {
        Tarea saved = tareaRepo.save(t);
        // agregar id tarea a proyecto.tareasIds si no está
        Proyecto p = proyectoRepo.findById(t.getProyectoId()).orElse(null);
        if (p != null) {
            if (!p.getTareasIds().contains(saved.getId())) {
                p.getTareasIds().add(saved.getId());
                proyectoRepo.save(p);
            }
        }
        return saved;
    }

    public void eliminar(String id) {
        Tarea t = tareaRepo.findById(id).orElse(null);
        if (t != null) {
            // remover referencia del proyecto
            Proyecto p = proyectoRepo.findById(t.getProyectoId()).orElse(null);
            if (p != null && p.getTareasIds().contains(id)) {
                p.getTareasIds().remove(id);
                proyectoRepo.save(p);
            }
            tareaRepo.deleteById(id);
        }
    }
}
