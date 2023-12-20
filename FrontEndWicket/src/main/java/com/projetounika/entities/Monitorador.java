package com.projetounika.entities;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    private Instant Data_nascimento = null;
    private String tipo;
    private boolean Ativo = true;

    private final List<Endereco> enderecos= new ArrayList<>();

    public Monitorador(){}

    public Monitorador(Long id, String tipo ,String TipoId, String nome, String email, String TipoIR, Instant data_nascimento, boolean ativo) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;

        if (tipo == "Fisica") {
            this.cpf = TipoId;
            this.Data_nascimento = data_nascimento;
            this.rg = TipoIR;

        }else {
            this.cnpj = TipoId;
            this.inscricao = TipoIR;

        }
        this.Ativo = ativo;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }


    public String getCpf() {
        return cpf;
    }



    public String setTipoId(String TipoId){
        if (this.tipo.equals("Fisica")){
            return this.cpf = TipoId;

        }
        return this.cnpj = TipoId;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }


    public String getinscricao() {
        return inscricao;
    }

    public void setRg(String rg) {
        rg = rg;
    }

    public void setInscricao_Estadual(String inscricao_Estadual) {
        this.inscricao = inscricao_Estadual;
    }

    public Instant getData_nascimento() {
        return Data_nascimento;
    }
    public static Instant parseInstant(String dateString) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateString));
    }

    public Instant setTipoData(Instant TipoData){
        if (tipo == "Fisica"){
            return this.Data_nascimento = TipoData;
        }
        return null;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
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
