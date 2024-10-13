package com.calculadora_final.calculadora.repositories;

import com.calculadora_final.calculadora.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar usuário por email
    Optional<Usuario> findByEmail(String email);
}
