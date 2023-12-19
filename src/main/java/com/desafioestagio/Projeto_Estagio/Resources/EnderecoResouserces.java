package com.desafioestagio.Projeto_Estagio.Resources;



import com.desafioestagio.Projeto_Estagio.Services.EnderecoServices;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.Services.Relatorios.RelatoriosServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoResouserces {

    @Autowired
    private EnderecoServices services;
    @Autowired
    private MonitoradorServices monitorador ;

    @Autowired
    private RelatoriosServices relatoriosServices ;




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
        if (!services.Validador(obj)) {

        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Void>delete(@PathVariable Long id){
        if(services.ValidadorMonitorador(id)) {
            services.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Endereco>update(@PathVariable Long id, @RequestBody Endereco obj){
        obj = services.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/relatorio/pdfs" )
    public ResponseEntity<String> GerarPDFEndereco( HttpServletResponse response){
        byte [] bytes = relatoriosServices.exportaPDFEndereco();
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        String base64Pdf = "data:application/pdf;base64," + Base64.encodeBase64String(bytes);
        return ResponseEntity.ok(base64Pdf);
    }


}
