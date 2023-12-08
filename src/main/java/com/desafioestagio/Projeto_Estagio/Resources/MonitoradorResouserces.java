package com.desafioestagio.Projeto_Estagio.Resources;


import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
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
    public  ResponseEntity<Monitorador>insert(@RequestBody Monitorador obj){
            obj = services.insert(obj);
         URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);


    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void>delete(@PathVariable Long id){
        services.delete(id);
        return  ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Monitorador>update(@PathVariable Long id, @RequestBody Monitorador obj){
        obj = services.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

}
