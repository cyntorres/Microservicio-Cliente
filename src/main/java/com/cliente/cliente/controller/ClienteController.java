package com.cliente.cliente.controller;

import org.springframework.http.ResponseEntity;

// El Controlador es el Mesero así que necesita conocer el Menú (el Modelo)
// Y poder hablar con el Chef (el Service).
// Por lo tanto, es importante importar el modelo y el service

import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.service.ClienteService;

// ===============================
// IMPORTACIONES SPRING
// ===============================
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// ===============================
// VALIDACIONES
// ===============================
import jakarta.validation.Valid;
import java.util.List;

/**
 * ===============================
 * CONTROLLER
 * ===============================
 *
 * Maneja las solicitudes HTTP del cliente.
 *
 * - Se utilizan ResponseEntity
 * - Se aplican códigos HTTP correctos
 * - Se integran validaciones (@Valid)
 * **/

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ==========================================================
    // LISTA TODOS LOS CLIENTES
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerClientes() {

        List<Cliente> clientes = clienteService.obtenerTodos();

        return ResponseEntity.ok(clientes); // 200 OK
    }

    // ==========================================================
    // BUSCA POR ID
    // ==========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {

        Cliente cliente = clienteService.obtenerPorId(id);

        return ResponseEntity.ok(cliente); // 200 OK
    }

    // ==========================================================
    // CREA UN CLIENTE
    // ==========================================================

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {

        Cliente nuevoCliente = clienteService.guardar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente); // 201
    }

    // ==========================================================
    // ACTUALIZA UN CLIENTE
    // ==========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {

        Cliente clienteActualizado = clienteService.actualizar(id, cliente);

        return ResponseEntity.ok(clienteActualizado); // 200 OK
    }

    // ==========================================================
    // ELIMINA UN CLIENTE
    // ==========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {

        clienteService.eliminar(id);

        return ResponseEntity.noContent().build(); // 204
    }

}