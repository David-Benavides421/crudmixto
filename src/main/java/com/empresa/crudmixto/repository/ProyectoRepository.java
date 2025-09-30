package com.empresa.crudmixto.repository;

import com.empresa.crudmixto.entity.Proyecto;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProyectoRepository extends MongoRepository<Proyecto, String> {
    List<Proyecto> findByEmpleadoId(Long empleadoId);
}
