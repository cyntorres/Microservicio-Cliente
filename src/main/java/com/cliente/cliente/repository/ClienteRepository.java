package com.cliente.cliente.repository;

// Importo la clase Cliente para que sea gestionada por el repository.
import com.cliente.cliente.model.Cliente;

// JpaRepository proporciona métodos CRUD ya implementados automáticamente.
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository<Cliente, Long> significa:
 * - Cliente → la entidad que se manejará
 * - Long → el tipo de dato de la clave primaria (id)
 *
 * Métodos disponibles automáticamente:
 * - save() → guardar o actualizar
 * - findAll() → listar todos
 * - findById() → buscar por id
 * - deleteById() → eliminar por id
 **/

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
