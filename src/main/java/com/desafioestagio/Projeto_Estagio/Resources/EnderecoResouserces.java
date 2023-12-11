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
@RequestMapping(value = "/endereco")
public class EnderecoResouserces {

    @Autowired
    private EnderecoServices services;
    @Autowired
    MonitoradorServices monitorador ;


    @GetMapping
    public ResponseEntity<List<Endereco>>findAll(){
        List<Endereco> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Endereco>findByid(@PathVariable Long id){
        Endereco obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public  ResponseEntity<Endereco>insert(@Valid @RequestBody Endereco obj){
        Monitorador moni = monitorador.ultimo();
        obj.setMonitorador(moni);
        System.out.println(moni);
        Endereco end = services.insert(obj);
        return ResponseEntity.ok(end);
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void>delete(@PathVariable Long id){
        services.delete(id);
        return  ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Endereco>update(@PathVariable Long id, @RequestBody Endereco obj){
        obj = services.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

}
