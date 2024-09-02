package rrhh.recursoshumanosbackend.exepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // Indica que esta excepción debería retornar un código de estado HTTP 404 (Not Found).
public class RecursoNoEncontradoExcepcion extends Throwable { // Define una clase de excepción personalizada que extiende de Throwable.

    // Constructor que recibe un mensaje de error cuando se lanza la excepción.
    public RecursoNoEncontradoExcepcion(String mensaje) {
        super(mensaje); // Llama al constructor de la clase padre (Throwable) con el mensaje de error.
    }
}
