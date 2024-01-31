package com.desafioestagio.Projeto_Estagio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Endereço pode ser vazio")
    private String endereco;
    private String numero;
    private String cep;

    private String telefone;
    @NotNull(message = " Cidade não pode ser vazio ")
    private String cidade;
    @NotNull(message = " Estado Não pode ser vazio")
    private String estado;
    @NotNull(message = "Bairro Não pode ser vazio")
    private String bairro;

    @NotNull(message = "Principal Não pode ser vazio")
    private Boolean principal;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "monitorador_id")
    private  Monitorador monitorador;

    public Endereco(){

    }

    public Endereco(Long id, String endereco, String numero, String cep, String telefone, String cidade, String estado, Boolean principal,String bairro ,Monitorador monitorador) {
        this.id = id;
        this.endereco = endereco;
        this.numero = numero;
        this.cep = cep;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.principal = principal;
        this.monitorador = monitorador;
        this.bairro = bairro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Monitorador getMonitorador() {
        return monitorador;
    }

    public void setMonitorador(Monitorador monitorador) {
        this.monitorador = monitorador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", cep='" + cep + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", principal=" + principal +
                '}';
    }
}
