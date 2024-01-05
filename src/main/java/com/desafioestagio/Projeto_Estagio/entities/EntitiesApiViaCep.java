package com.desafioestagio.Projeto_Estagio.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EntitiesApiViaCep {
    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("numero")
    private String numero;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("localidade")
    private String localidade; // cidade

    @JsonProperty("uf")
    private String uf; // estado

    @JsonProperty("bairro")
    private String bairro;



    public Endereco toEnderecoEntity() {
        Endereco enderecoEntity = new Endereco();
        enderecoEntity.setEndereco(logradouro);
        enderecoEntity.setNumero(numero);
        enderecoEntity.setCep(cep);
        enderecoEntity.setCidade(localidade);
        enderecoEntity.setEstado(uf);
        enderecoEntity.setBairro(bairro);

        return enderecoEntity;
    }
}
