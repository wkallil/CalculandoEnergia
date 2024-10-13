package com.calculadora_final.calculadora.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
@Entity
public class Aparelho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private double potencia;
    //em watts
    @Column(nullable = false)
    private double horasUsoDiario;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    public double getConsumoDiario() {
        return (potencia / 100.0) * horasUsoDiario; //convertendo potência de watts para kWh
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getHorasUsoDiario() {
        return horasUsoDiario;
    }

    public void setHorasUsoDiario(double horasUsoDiario) {
        this.horasUsoDiario = horasUsoDiario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
