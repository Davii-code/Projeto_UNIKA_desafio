package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.EnderecoRepositorys;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServices {

    @Autowired
    private EnderecoRepositorys enderecoRepositorys;


    public List<Endereco> findAll(){
        return enderecoRepositorys.findAll();
    }

    public Endereco findById(Long id){
        Optional<Endereco>obj = enderecoRepositorys.findById(id);
        return obj.get();
    }

    public Endereco insert(Endereco obj){
        return enderecoRepositorys.save(obj);
    }

}
