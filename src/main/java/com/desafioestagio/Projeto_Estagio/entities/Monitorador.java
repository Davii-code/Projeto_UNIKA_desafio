package com.desafioestagio.Projeto_Estagio.entities;

import com.desafioestagio.Projeto_Estagio.Validator.IRValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "tb_monitorador")
public class Monitorador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CNPJ
    private String cnpj = null;
    @CPF
    private String cpf = null;
    @NotEmpty
    private String nome;
    private String email = null;

    @IRValidator()
    private String Rg = null;
    @IRValidator()
    private String inscricao_Estadual = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "GMT")
    private Instant Data_nascimento = null;
    private String tipo;
    private boolean Ativo = true;

    @OneToMany(mappedBy = "monitorador")
    private List<Endereco> enderecos= new ArrayList<>();

    public Monitorador(){}

    public Monitorador(Long id, String tipo ,String TipoId, String nome, String email, String TipoIR, Instant data_nascimento, boolean ativo) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;

       if (tipo == "Fisica") {
           this.cpf = TipoId;
           this.Data_nascimento = data_nascimento;
           this.Rg = TipoIR;

       }else {
           this.cnpj = TipoId;
           this.inscricao_Estadual = TipoIR;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return Rg;
    }


    public String getInscricao_Estadual() {
        return inscricao_Estadual;
    }

    public void setRg(String rg) {
        Rg = rg;
    }

    public void setInscricao_Estadual(String inscricao_Estadual) {
        this.inscricao_Estadual = inscricao_Estadual;
    }

    public Instant getData_nascimento() {
        return Data_nascimento;
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
                ", Rg='" + Rg + '\'' +
                ", inscricao_Estadual='" + inscricao_Estadual + '\'' +
                ", Data_nascimento=" + Data_nascimento +
                ", tipo='" + tipo + '\'' +
                ", Ativo=" + Ativo +
                '}';
    }
}
