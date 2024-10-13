package com.calculadora_final.calculadora.controllers;

import com.calculadora_final.calculadora.models.Perfil;
import com.calculadora_final.calculadora.models.Usuario;
import com.calculadora_final.calculadora.repositories.PerfilRepository;
import com.calculadora_final.calculadora.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {


    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos os perfis
    @GetMapping
    public List<Perfil> listarPerfis() {
        return perfilRepository.findAll();
    }

    // Obter um perfil por ID
    @GetMapping("/{id}")
    public Perfil getPerfil(@PathVariable Long id) {
        return perfilRepository.findById(id).orElseThrow();
    }

    // Criar um perfil associado a um usuário
    @PostMapping("/usuario/{usuarioId}")
    public Perfil criarPerfil(@PathVariable Long usuarioId, @RequestBody Perfil perfil) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        perfil.setUsuario(usuario);
        return perfilRepository.save(perfil);
    }

    // Atualizar um perfil existente
    @PutMapping("/{id}")
    public Perfil atualizarPerfil(@PathVariable Long id, @RequestBody Perfil perfilAtualizado) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow();
        perfil.setNome(perfilAtualizado.getNome());
        return perfilRepository.save(perfil);
    }

    // Deletar um perfil
    @DeleteMapping("/{id}")
    public void deletarPerfil(@PathVariable Long id) {
        perfilRepository.deleteById(id);
    }
}

