package com.desafioestagio.Projeto_Estagio.Resources;


import com.desafioestagio.Projeto_Estagio.Services.EnderecoServices;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.Services.Relatorios.RelatoriosServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/monitorador",produces = "application/json; charset=UTF-8")
public class MonitoradorResouserces {

    @Autowired
    private MonitoradorServices services;

    @Autowired
    private EnderecoServices enderecoServices;

    @Autowired
    private RelatoriosServices relatoriosServices;



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


    @GetMapping(value = "/{id}/enderecos")
    public ResponseEntity<List<Endereco>> findByEnde(@PathVariable Long id) {
        Monitorador monitorador = services.findById(id);

        if (monitorador != null) {
            List<Endereco> enderecos = monitorador.getEnderecos();
            return ResponseEntity.ok().body(enderecos);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        return  ResponseEntity.status(200).build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Monitorador>update(@PathVariable Long id, @RequestBody Monitorador obj){
        obj = services.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/relatorio/pdfs")
    public void GerarPDFMonitorador(    @RequestParam(required = false) String nome,
                                        @RequestParam(required = false) String cnpj,
                                        @RequestParam(required = false) String cpf,
                                        @RequestParam(required = false) Long id, HttpServletResponse response) throws IOException {

        byte[] bytes;

        if (nome != null){
            List<Monitorador> monitoradores = services.findByNome(nome);
            bytes = relatoriosServices.exportaPDFMonitorador(monitoradores);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream().write(bytes);
        } else if (cnpj != null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findBycnpj(cnpj));
            bytes = relatoriosServices.exportaPDFMonitorador(monitoradores);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream().write(bytes);
        } else if (cpf!=null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findByCpf(cpf));
            bytes = relatoriosServices.exportaPDFMonitorador(monitoradores);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream().write(bytes);
        } else if (id != null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findById(id));
            bytes = relatoriosServices.exportaPDFMonitorador(monitoradores);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream().write(bytes);
        } else if (nome == null && cnpj == null && cpf == null && id == null) {
            List<Monitorador> monitoradores = services.findAll();
            bytes = relatoriosServices.exportaPDFMonitorador(monitoradores);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream().write(bytes);
        }


    }

    @GetMapping("/relatorio/excel")
    public ResponseEntity<InputStreamResource> exportarMonitoradoresParaExcel( @RequestParam(required = false) String nome, @RequestParam(required = false) String cnpj, @RequestParam(required = false) String cpf, @RequestParam(required = false) Long id) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (nome!= null){
            List<Monitorador> monitoradores =services.findByNome(nome);
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel(monitoradores);
        } else if (cnpj != null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findBycnpj(cnpj));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel(monitoradores);
        } else if (cpf!= null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findByCpf(cpf));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel(monitoradores);
        } else if (id!=null) {
            List<Monitorador> monitoradores = Collections.singletonList(services.findById(id));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel(monitoradores);
        } else if (nome == null && cnpj == null && cpf == null && id == null) {
            List<Monitorador> monitoradores =services.findAll();
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel(monitoradores);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=monitoradores.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
    }

    @PostMapping("/importar-excel")
    public String importarMonitoradoresDoExcel() {
        try {
            List<Monitorador> monitoradores = services.criarExcel();
            return "Importação bem-sucedida";
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente
            return "Erro durante a importação: " + e.getMessage();
        }
    }




    //Filtros

    @GetMapping(value = "/filtroNome/{nome}")
    public ResponseEntity<List<Monitorador>>findByNome(@PathVariable String nome){
        List<Monitorador> obj = services.findByNome(nome);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/filtroCnpj/{cnpj}")
    public ResponseEntity<Monitorador>findBycnpj(@PathVariable String cnpj){
        Monitorador obj = services.findBycnpj(cnpj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/filtroCpf/{cpf}")
    public ResponseEntity<Monitorador>findByCpf(@PathVariable String cpf){
        Monitorador obj = services.findByCpf(cpf);
        return ResponseEntity.ok().body(obj);
    }


}
