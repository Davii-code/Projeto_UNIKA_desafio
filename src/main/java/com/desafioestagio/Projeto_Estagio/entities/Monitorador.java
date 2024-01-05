package com.desafioestagio.Projeto_Estagio.entities;

import com.desafioestagio.Projeto_Estagio.Validator.IRValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
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
    @IRValidator()
    private String cnpj = null;
    @CPF
    @IRValidator()
    private String cpf = null;
    @NotEmpty
    private String nome;
    private String email = null;

     @IRValidator()
    private String rg;
    @IRValidator()
    private String inscricao = null;

    private String Data_nascimento = null;
    private String tipo;
    private boolean Ativo;

    @OneToMany(mappedBy = "monitorador")
    private List<Endereco> enderecos= new ArrayList<>();

    public Monitorador(){}

    public Monitorador(Long id, String tipo ,String cnpj,String cpf, String nome, String email, String rg,String inscricao, String data_nascimento, boolean ativo) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }


    public String getCpf() {
        return cpf;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        return rg;
    }


    public String getInscricao() {
        return inscricao;
    }

    public void setRg(String rg) {this.rg = rg;}

    public void setInscricaol(String inscricao_Estadual) {
        this.inscricao = inscricao_Estadual;
    }

    public String getData_nascimento() {
        return Data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        Data_nascimento = data_nascimento;
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
        this.Ativo = ativo;
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
