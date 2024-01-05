package com.desafioestagio.Projeto_Estagio.Resources;


import com.desafioestagio.Projeto_Estagio.Services.ConsumoApi;
import com.desafioestagio.Projeto_Estagio.Services.EnderecoServices;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.Services.Relatorios.RelatoriosServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.EntitiesApiViaCep;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/endereco",produces = "application/json; charset=UTF-8")
public class EnderecoResouserces {

    @Autowired
    private EnderecoServices services;
    @Autowired
    private MonitoradorServices monitorador;

    @Autowired
    private RelatoriosServices relatoriosServices;

    @Autowired
    private ConsumoApi consumoApi;

    @GetMapping
    public ResponseEntity<List<Endereco>> findAll() {
        List<Endereco> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Endereco> findByid(@PathVariable Long id) {
        Endereco obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    public ResponseEntity<Endereco> insert(@RequestBody Endereco obj) {
        Monitorador moni = monitorador.ultimo();
        obj.setMonitorador(moni);
        Endereco endereco = services.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(endereco);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (services.ValidadorMonitorador(id)) {
            services.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco obj) {
        obj = services.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/buscarCEP/{cep}")
    public ResponseEntity<Endereco> BuscaApi(@PathVariable String cep) {
        EntitiesApiViaCep entitiesApiViaCep = consumoApi.obterDados(cep);
        Endereco endereco = entitiesApiViaCep.toEnderecoEntity();
        return ResponseEntity.ok().body(endereco);
    }

    @GetMapping(value = "/relatorio/pdfs")
    public void GerarPDFEndereco(@RequestParam (required = false) Long id, HttpServletResponse response) throws IOException {
        Monitorador monitorador1 = monitorador.findById(id);
        List<Endereco>  enderecos = monitorador1.getEnderecos();;

            byte[] bytes = relatoriosServices.exportaPDFEndereco(enderecos);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioEndereco.pdf");
            response.getOutputStream().write(bytes);



    }

    @GetMapping(value = "/relatorio/excel")
    public ResponseEntity<InputStreamResource> esportaEnderecoParaExcel(@RequestParam (required = false) Long id) {
        ByteArrayOutputStream byteArrayOutputStream = services.exportaEnderecoParaExcel(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=monitoradores.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
    }

}
