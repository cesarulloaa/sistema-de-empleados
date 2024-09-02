package rrhh.recursoshumanosbackend.contolador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rrhh.recursoshumanosbackend.exepcion.RecursoNoEncontradoExcepcion;
import rrhh.recursoshumanosbackend.modelo.Empleado;
import rrhh.recursoshumanosbackend.servicio.IEmpleadoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador REST que maneja solicitudes HTTP y devuelve datos JSON.
@RequestMapping("/rh-app") // Mapea las solicitudes que comienzan con "/rh-app" a los métodos de esta clase.
@CrossOrigin(value = "http://localhost:3000") // Permite solicitudes CORS desde el frontend que corre en "http://localhost:3000".
public class EmpleadoControlador {

    // Logger para registrar información de las operaciones en este controlador.
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    // Inyección de dependencias para usar el servicio de empleado.
    @Autowired
    private IEmpleadoServicio empleadoServicio;

    // Método para obtener la lista de todos los empleados.
    @GetMapping("/empleados")
    public List<Empleado> obternerEmpleado() {
        var empleados = empleadoServicio.listarEmpleados(); // Llama al servicio para listar los empleados.
        empleados.forEach((empleado -> logger.info(empleado.toString()))); // Registra información de cada empleado.
        return empleados; // Retorna la lista de empleados.
    }

    // Método para agregar un nuevo empleado.
    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        logger.info("Empleado a agregar: " + empleado); // Registra la información del empleado que se va a agregar.
        return empleadoServicio.guardarEmpleado(empleado); // Llama al servicio para guardar el nuevo empleado y lo retorna.
    }

    // Método para obtener un empleado por su ID.
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id) throws RecursoNoEncontradoExcepcion {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id); // Busca el empleado por su ID.
        if (empleado == null) throw new RecursoNoEncontradoExcepcion("No se encontró el id " + id); // Lanza una excepción si no se encuentra.
        return ResponseEntity.ok(empleado); // Retorna el empleado encontrado envuelto en un ResponseEntity.
    }

    // Método para actualizar un empleado existente.
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id,
                                                       @RequestBody Empleado empleadoRecibido) throws RecursoNoEncontradoExcepcion {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id); // Busca el empleado por su ID.
        if (empleado == null) throw new RecursoNoEncontradoExcepcion("No se encontró el id " + id); // Lanza una excepción si no se encuentra.

        // Actualiza los campos del empleado con los valores recibidos.
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());

        empleadoServicio.guardarEmpleado(empleado); // Guarda los cambios en la base de datos.
        return ResponseEntity.ok(empleado); // Retorna el empleado actualizado envuelto en un ResponseEntity.
    }

    // Método para eliminar un empleado por su ID.
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable Integer id) throws RecursoNoEncontradoExcepcion {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id); // Busca el empleado por su ID.
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id); // Lanza una excepción si no se encuentra.
        }
        empleadoServicio.eliminarEmpleado(empleado); // Llama al servicio para eliminar el empleado.

        // Crear un mapa de respuesta JSON con la confirmación de la eliminación.
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);

        // Retorna el ResponseEntity con el mapa de respuesta.
        return ResponseEntity.ok(respuesta);
    }
}
