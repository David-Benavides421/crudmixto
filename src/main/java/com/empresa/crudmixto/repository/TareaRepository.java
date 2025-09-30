package com.empresa.crudmixto.repository;

import com.empresa.crudmixto.entity.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TareaRepository extends MongoRepository<Tarea, String> {
    List<Tarea> findByProyectoId(String proyectoId);
}
