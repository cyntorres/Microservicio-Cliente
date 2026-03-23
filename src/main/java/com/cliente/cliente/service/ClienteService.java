package com.cliente.cliente.service;

// ===============================
// IMPORTACIONES DEL PROYECTO
// ===============================
// Se importa la entidad Cliente, que representa cada registro de la tabla CLIENTE.
import com.cliente.cliente.model.Cliente;

// Se importa el repository, que se encarga de la comunicación con la base de datos.
import com.cliente.cliente.repository.ClienteRepository;
import com.cliente.cliente.exception.ResourceNotFoundException;

// ===============================
// IMPORTACIONES DE SPRING
// ===============================
// Marca esta clase como un servicio dentro del contexto de Spring.
import org.springframework.stereotype.Service;

// ===============================
// IMPORTACIONES DE LOMBOK
// ===============================
// @Slf4j permite registrar logs de manera profesional.
import lombok.extern.slf4j.Slf4j;

// ===============================
// IMPORTACIONES DE JAVA
// ===============================
import java.util.List;

/**
 * ===============================
 * SERVICIO
 * ===============================
 *
 * Esta clase contiene la lógica de negocio del microservicio.
 */

@Service
@Slf4j
public class ClienteService {

    /**
     * Referencia al repository.
     * Esta variable permitirá acceder a los métodos CRUD provistos por JPA.
     */
    private final ClienteRepository clienteRepository;

    /**
     * Constructor con inyección de dependencias.
     * Spring Boot entrega automáticamente una instancia de ClienteRepository.
     */
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ==========================================================
    // OBTIENE TODOS LOS CLIENTES
    // ==========================================================

    /**
     * Este método recupera todos los clientes registrados en la base de datos.
     *
     * @return lista de clientes
     */
    public List<Cliente> obtenerTodos() {
        log.info("Solicitando el listado completo de clientes.");

        List<Cliente> clientes = clienteRepository.findAll();

        log.info("Se encontraron {} clientes registrados.", clientes.size());
        return clientes;
    }

    // ==========================================================
    // OBTIENE UN CLIENTE POR SU ID
    // ==========================================================

    /**
     * Este método busca un cliente por su id.
     * Si no existe, se lanza una excepción personalizada.
     *
     * @param id identificador del cliente
     * @return cliente encontrado
     */
    public Cliente obtenerPorId(Long id) {
        log.info("Buscando el cliente ID: {}", id);

        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró un cliente con ID: {}", id);
                    return new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
                });
    }

    // ==========================================================
    // GUARDA UN CLIENTE
    // ==========================================================

    /**
     * Este método guarda un cliente en la base de datos.
     *
     * @param cliente cliente a registrar
     * @return cliente guardado
     */
    public Cliente guardar(Cliente cliente) {
        log.info("Guardando cliente: {}", cliente.getNombre());

        Cliente clienteGuardado = clienteRepository.save(cliente);

        log.info("Cliente guardado correctamente con ID: {}", clienteGuardado.getId());
        return clienteGuardado;
    }

    // ==========================================================
    // ELIMINA UN CLIENTE POR SU ID
    // ==========================================================

    /**
     * Este método elimina un cliente utilizando su id.
     * Si el cliente no existe, se lanza una excepción personalizada.
     *
     * @param id identificador del cliente a eliminar
     */
    public void eliminar(Long id) {
        log.info("Intentando eliminar cliente ID: {}", id);

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede eliminar. Cliente no encontrado, ID: {}", id);
                    return new ResourceNotFoundException("Cliente no encontrado, ID: " + id);
                });

        clienteRepository.delete(clienteExistente);

        log.info("Cliente eliminado correctamente, ID: {}", id);
    }

    // ==========================================================
    // ACTUALIZA UN CLIENTE EXISTENTE
    // ==========================================================

    /**
     * Este método actualiza un cliente existente a partir de su id.
     * Si el cliente no existe, se lanza una excepción personalizada.
     *
     * @param id                 identificador del cliente a actualizar
     * @param clienteActualizado datos nuevos del cliente
     * @return cliente actualizado
     */
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        log.info("Intentando actualizar cliente ID: {}", id);

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se puede actualizar. Cliente no encontrado, ID: {}", id);
                    return new ResourceNotFoundException("Cliente no encontrado, ID: " + id);
                });

        // Se actualizan los campos con los nuevos valores recibidos.
        clienteExistente.setRut(clienteActualizado.getRut());
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setApellido(clienteActualizado.getApellido());
        clienteExistente.setEmail(clienteActualizado.getEmail());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());

        Cliente clienteGuardado = clienteRepository.save(clienteExistente);

        log.info("Cliente actualizado correctamente con ID: {}", clienteGuardado.getId());
        return clienteGuardado;
    }

}