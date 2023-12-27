package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.MonitoradorRepositorys;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.DataBaseExeception;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.ResourceNotFoundException;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoradorServices {

    @Autowired
    private MonitoradorRepositorys repository;


    public List<Monitorador>findAll(){return repository.findAll();}

    public Monitorador findById(Long id){
        Optional<Monitorador>obj = repository.findById(id);
        return obj.orElseThrow(()->new ResourceNotFoundException(id));
    }




    public Monitorador insert(Monitorador obj){
        return repository.save(obj);
    }
    public void delete(Long id ){
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseExeception(e.getMessage());
        }
    }

    public Monitorador ultimo(){
        return repository.findTopByOrderByIdDesc();
    }

    public Monitorador update(Long id, Monitorador obj) throws EntityNotFoundException {
        Monitorador entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }


 public boolean ValidadorIgualID(Monitorador obj){
        if ("Juridica".equals(obj.getTipo())) {
           return repository.existsByCnpj(obj.getCnpj()) && repository.existsByInscricao(obj.getinscricao());
        }else{
            return  repository.existsByCpf(obj.getCpf()) && repository.existsByRg(obj.getRg());
        }
    }



    public void updateData(Monitorador entity, Monitorador obj) {
        entity.setNome(obj.getNome());
        entity.setEmail(obj.getEmail());
        entity.setInscricao_Estadual(obj.getinscricao());
        entity.setAtivo(obj.isAtivo());
        entity.setRg(obj.getRg());
        entity.setCpf(obj.getCpf());
        entity.setCnpj(obj.getCnpj());
        entity.setData_nascimento(obj.getData_nascimento());
    }



}
