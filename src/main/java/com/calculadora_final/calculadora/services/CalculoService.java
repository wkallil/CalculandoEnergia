package com.calculadora_final.calculadora.services;

import com.calculadora_final.calculadora.models.Aparelho;
import com.calculadora_final.calculadora.models.Historico;
import com.calculadora_final.calculadora.models.Perfil;
import com.calculadora_final.calculadora.models.Usuario;
import com.calculadora_final.calculadora.repositories.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalculoService {

    private static final double VALOR_KWH = 0.60;
    private static final double BANDEIRA = 1.1;

    @Autowired
    private HistoricoRepository historicoRepository;

    public double calcularValorPerfil(Perfil perfil) {
        List<Aparelho> aparelhos = perfil.getAparelhos();
        double consumoTotalMensal = aparelhos.stream().mapToDouble(Aparelho::getConsumoDiario).sum() * 30; // Consumo mensal
        return consumoTotalMensal * VALOR_KWH * BANDEIRA;
    }

    public double calcularValorUsuario(Usuario usuario) {
        return usuario.getPerfis().stream()
                .mapToDouble(this::calcularValorPerfil)
                .sum();
    }

    public Historico registrarHistorico(Perfil perfil, double valorTotal) {
        Historico historico = new Historico();
        historico.setData(LocalDate.now());
        historico.setValorTotal(valorTotal);
        historico.setPerfil(perfil);
        return historicoRepository.save(historico);
    }
}
