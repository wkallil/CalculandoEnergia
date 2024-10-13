package com.calculadora_final.calculadora.controllers;

import com.calculadora_final.calculadora.models.Historico;
import com.calculadora_final.calculadora.models.Perfil;
import com.calculadora_final.calculadora.models.Usuario;
import com.calculadora_final.calculadora.repositories.PerfilRepository;
import com.calculadora_final.calculadora.repositories.UsuarioRepository;
import com.calculadora_final.calculadora.services.CalculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CalculoService calculoService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id);
    }

    // Método que recebe o email e retorna os perfis do usuário ou cria um novo
    @PostMapping("/login")
    public List<Perfil> loginOuCriarUsuario(@RequestParam String email) {
        // Verifica se o usuário já existe pelo email
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);

        if (usuarioExistente.isPresent()) {
            // Se o usuário já existe, retorna os perfis dele
            return usuarioExistente.get().getPerfis();
        } else {
            // Se o usuário não existe, cria um novo usuário sem perfis
            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email);
            Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

            // Retorna uma lista vazia, já que o usuário não possui perfis ainda
            return List.of();
        }
    }
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @GetMapping("/{id}/perfis/{perfilId}/calcular")
    public double calcularContaPerfil(@PathVariable Long id, @PathVariable Long perfilId) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        Perfil perfil = usuario.getPerfis().stream()
                .filter(p -> p.getId().equals(perfilId))
                .findFirst()
                .orElseThrow();
        double valor = calculoService.calcularValorPerfil(perfil);
        calculoService.registrarHistorico(perfil, valor);
        return valor;
    }

    @GetMapping("/{id}/calcular")
    public double calcularContaUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return calculoService.calcularValorUsuario(usuario);
    }

    @GetMapping("/{id}/historico")
    public List<Historico> getHistoricoUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return usuario.getPerfis().stream()
                .flatMap(perfil -> perfil.getHistoricos().stream())
                .toList();
    }
}
