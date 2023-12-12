package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.EnderecoRepositorys;
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


    public void delete(Long id ){
        try {
            enderecoRepositorys.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseExeception(e.getMessage());
        }
    }

    public void deleteListEnd(Long id ){
     List<Long> id_End = enderecoRepositorys.deleteEnderecoByIdIn(id);
        for (int i = 0; i < id_End.size(); i++) {
            try {
                enderecoRepositorys.deleteById(id_End.get(i));
            }catch (EmptyResultDataAccessException e){
                throw new ResourceNotFoundException(id);
            } catch (DataIntegrityViolationException e){
                throw new DataBaseExeception(e.getMessage());
            }
        }

    }

    public boolean ValidadorMonitorador(Long id) {
        Long id_monitorador = enderecoRepositorys.VerificaMonitorador(id);
        List<Long> id_End = enderecoRepositorys.ValidadorEstrangeiro(id_monitorador);
        if (id_End.size()> 1 ){
            return true;
        }
        return false;
    }
    public boolean Validador(Endereco obj){
        if (enderecoRepositorys.existsByPrincipal(obj.getPrincipal()) && enderecoRepositorys.existsByCep(obj.getCep())){
            return true;
        }
        return false;
    }

    public Endereco update(Long id, Endereco obj){
        try {
            Endereco entity = enderecoRepositorys.getReferenceById(id);
            updateData(entity, obj);
            return enderecoRepositorys.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Endereco entity, Endereco obj) {
        entity.setEndereco(obj.getEndereco());
        entity.setNumero(obj.getNumero());
        entity.setCep(obj.getCep());
        entity.setEstado(obj.getEstado());
        entity.setTelefone(obj.getTelefone());
        entity.setCidade(obj.getCidade());
        entity.setPrincipal(obj.getPrincipal());

    }

}
