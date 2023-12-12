package com.desafioestagio.Projeto_Estagio.Resources;


import com.desafioestagio.Projeto_Estagio.Services.EnderecoServices;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/monitorador")
public class MonitoradorResouserces {

    @Autowired
    private MonitoradorServices services;

    @Autowired
    private EnderecoServices enderecoServices;





    @GetMapping
    public ResponseEntity<List<Monitorador>>findAll(){
        List<Monitorador> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Monitorador>findByid(@PathVariable Long id){
        Monitorador obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    public  ResponseEntity<Monitorador>insert(@Valid @RequestBody Monitorador obj){
        if (!services.ValidadorIgualID(obj)) {
            obj = services.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).body(obj);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/{id}/enderecos")
    public ResponseEntity<Endereco> insertEnd(@PathVariable Long id, @RequestBody Endereco endereco){

        Monitorador monitorador = services.findById(id);
        endereco.setMonitorador(monitorador);
        Endereco end = enderecoServices.insert(endereco);
        return ResponseEntity.ok(end);
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void>delete(@PathVariable Long id){
        enderecoServices.deleteListEnd(id);
        services.delete(id);
        return  ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Monitorador>update(@PathVariable Long id, @RequestBody Monitorador obj){
        obj = services.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

}
