package com.projetounika.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;
import org.apache.htrace.shaded.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Monitorador implements Serializable {


    private Long id;

    private String cnpj = null;

    private String cpf = null;

    private String nome;
    private String email = null;

    private String rg = null;
    private String inscricao = null;

    private String Data_nascimento = null;
    private String tipo;

    private boolean Ativo = true;

    private final List<Endereco> enderecos= new ArrayList<>();

    public Monitorador(){}

    public Monitorador(Long id, String tipo ,String cpf,String cnpj, String nome, String email, String rg,String inscricao,String data_nascimento, boolean ativo) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;

        if (tipo == "Fisica") {
            this.cpf = cpf;
            this.Data_nascimento = data_nascimento;
            this.rg = rg;

        }else {
            this.cnpj = cnpj;
            this.inscricao = inscricao;

        }
        this.Ativo = ativo;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }


    public String getCpf() {
        return cpf;
    }



    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRg() {
        return rg;
    }


    public String getinscricao() {
        return inscricao;
    }


    public String getData_nascimento() {
        return Data_nascimento;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public void setData_nascimento(String data_nascimento) {
        Data_nascimento = data_nascimento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monitorador that = (Monitorador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Monitorador{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", Rg='" + rg + '\'' +
                ", inscricao_Estadual='" + inscricao + '\'' +
                ", Data_nascimento=" + Data_nascimento +
                ", tipo='" + tipo + '\'' +
                ", Ativo=" + Ativo +
                '}';
    }
}
