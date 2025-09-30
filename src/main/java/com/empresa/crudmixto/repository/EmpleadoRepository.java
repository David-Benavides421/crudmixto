package com.empresa.crudmixto.repository;

import com.empresa.crudmixto.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByEmail(String email);
    List<Empleado> findByNombreContainingIgnoreCaseOrCargoContainingIgnoreCase(String nombre, String cargo);
}
