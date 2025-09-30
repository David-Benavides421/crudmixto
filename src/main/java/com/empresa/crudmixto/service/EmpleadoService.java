package com.empresa.crudmixto.service;

import com.empresa.crudmixto.entity.Empleado;
import com.empresa.crudmixto.repository.EmpleadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmpleadoService {
    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public List<Empleado> listar() {
        return repo.findAll();
    }

    public Empleado guardar(Empleado e) {
        // Validar email único
        if (e.getId() == null) { // creación
            if (repo.existsByEmail(e.getEmail())) {
                throw new DataIntegrityViolationException("Email ya registrado");
            }
        } else { // actualización: si cambia email, verificar
            Empleado existing = repo.findById(e.getId()).orElse(null);
            if (existing != null && !existing.getEmail().equals(e.getEmail()) && repo.existsByEmail(e.getEmail())) {
                throw new DataIntegrityViolationException("Email ya registrado");
            }
        }
        return repo.save(e);
    }

    public Empleado obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Empleado> buscar(String texto) {
        return repo.findByNombreContainingIgnoreCaseOrCargoContainingIgnoreCase(texto, texto);
    }
}
