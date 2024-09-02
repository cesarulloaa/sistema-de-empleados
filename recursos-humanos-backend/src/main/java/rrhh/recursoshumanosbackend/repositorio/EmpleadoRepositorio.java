package rrhh.recursoshumanosbackend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import rrhh.recursoshumanosbackend.modelo.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {

}
