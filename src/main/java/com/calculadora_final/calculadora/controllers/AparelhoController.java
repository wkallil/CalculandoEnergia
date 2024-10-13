package com.calculadora_final.calculadora.controllers;

import com.calculadora_final.calculadora.models.Aparelho;
import com.calculadora_final.calculadora.models.Perfil;
import com.calculadora_final.calculadora.repositories.AparelhoRepository;
import com.calculadora_final.calculadora.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aparelhos")
public class AparelhoController {

    @Autowired
    private AparelhoRepository aparelhoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    // Listar todos os aparelhos
    @GetMapping
    public List<Aparelho> listarAparelhos() {
        return aparelhoRepository.findAll();
    }

    // Obter um aparelho por ID
    @GetMapping("/{id}")
    public Aparelho getAparelho(@PathVariable Long id) {
        return aparelhoRepository.findById(id).orElseThrow();
    }

    // Criar um aparelho associado a um perfil
    @PostMapping("/perfil/{perfilId}")
    public Aparelho criarAparelho(@PathVariable Long perfilId, @RequestBody Aparelho aparelho) {
        Perfil perfil = perfilRepository.findById(perfilId).orElseThrow();
        aparelho.setPerfil(perfil);
        return aparelhoRepository.save(aparelho);
    }

    // Atualizar um aparelho existente
    @PutMapping("/{id}")
    public Aparelho atualizarAparelho(@PathVariable Long id, @RequestBody Aparelho aparelhoAtualizado) {
        Aparelho aparelho = aparelhoRepository.findById(id).orElseThrow();
        aparelho.setNome(aparelhoAtualizado.getNome());
        aparelho.setPotencia(aparelhoAtualizado.getPotencia());
        aparelho.setHorasUsoDiario(aparelhoAtualizado.getHorasUsoDiario());
        return aparelhoRepository.save(aparelho);
    }

    // Deletar um aparelho
    @DeleteMapping("/{id}")
    public void deletarAparelho(@PathVariable Long id) {
        aparelhoRepository.deleteById(id);
    }
}
