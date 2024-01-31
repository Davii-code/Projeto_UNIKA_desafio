package com.desafioestagio.Projeto_Estagio.Resources;


import com.desafioestagio.Projeto_Estagio.Services.EnderecoServices;
import com.desafioestagio.Projeto_Estagio.Services.MonitoradorServices;
import com.desafioestagio.Projeto_Estagio.Services.Relatorios.RelatoriosServices;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.ErrorResponse;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/monitorador", produces = "application/json; charset=UTF-8")
@CrossOrigin(origins = "http://localhost:4200")
public class MonitoradorResouserces {

    @Autowired
    private MonitoradorServices services;

    @Autowired
    private EnderecoServices enderecoServices;

    @Autowired
    private RelatoriosServices relatoriosServices;


    @GetMapping
    public ResponseEntity<List<Monitorador>> findAll() {
        List<Monitorador> list = services.findAll ();
        return ResponseEntity.ok ().body (list);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Monitorador> findByid(@PathVariable Long id) {
        Monitorador obj = services.findById (id);
        return ResponseEntity.ok ().body (obj);
    }


    @GetMapping(value = "/{id}/enderecos")
    public ResponseEntity<List<Endereco>> findByEnde(@PathVariable Long id) {
        Monitorador monitorador = services.findById (id);

        if (monitorador != null) {
            List<Endereco> enderecos = monitorador.getEnderecos ();
            return ResponseEntity.ok ().body (enderecos);
        } else {
            return ResponseEntity.notFound ().build ();
        }
    }


    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody Monitorador obj) {

        if (!services.ValidadorIgualID (obj)) {
            obj = services.insert (obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest ().path ("/{id}")
                    .buildAndExpand (obj.getId ()).toUri ();
            return ResponseEntity.created (uri).body (obj);
        } else {
            String errorMessage = "Dados já cadastrados";
            return ResponseEntity.status (HttpStatus.CONFLICT).body (new ErrorResponse (errorMessage));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult ().getFieldError ().getDefaultMessage ();

        return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (new ErrorResponse (errorMessage));
    }

    @PostMapping(value = "/{id}/enderecos")
    public ResponseEntity<?> insertEnd(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
        if (!services.ValidadorIgualIDEnd (endereco)) {
            Monitorador monitorador = services.findById (id);
            endereco.setMonitorador (monitorador);
            Endereco end = enderecoServices.insert (endereco);
            return ResponseEntity.ok (end);
        } else {
            String errorMessage = "Dados ja cadastrados";
            return ResponseEntity.status (HttpStatus.CONFLICT).body (new ErrorResponse (errorMessage));
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoServices.deleteListEnd (id);
        services.delete (id);
        return ResponseEntity.status (200).build ();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Monitorador obj) {

        if (!services.ValidadorIgualIDPUT (obj)) {
            obj = services.update (id, obj);
            return ResponseEntity.ok ().body (obj);
        } else {
            String errorMessage = "Dados ja cadastrados";
            return ResponseEntity.status (HttpStatus.CONFLICT).body (new ErrorResponse (errorMessage));
        }

    }

    @GetMapping(value = "/relatorio/pdfs")
    public void GerarPDFMonitorador(@RequestParam(required = false) String nome,
                                    @RequestParam(required = false) String cnpj,
                                    @RequestParam(required = false) String cpf,
                                    @RequestParam(required = false) Long id, HttpServletResponse response) throws IOException {

        byte[] bytes;

        if (nome != null) {
            List<Monitorador> monitoradores = services.findByNome (nome);
            bytes = relatoriosServices.exportaPDFMonitorador (monitoradores);
            response.setContentType (MediaType.APPLICATION_PDF_VALUE);
            response.setHeader ("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream ().write (bytes);
        } else if (cnpj != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findBycnpj (cnpj));
            bytes = relatoriosServices.exportaPDFMonitorador (monitoradores);
            response.setContentType (MediaType.APPLICATION_PDF_VALUE);
            response.setHeader ("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream ().write (bytes);
        } else if (cpf != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findByCpf (cpf));
            bytes = relatoriosServices.exportaPDFMonitorador (monitoradores);
            response.setContentType (MediaType.APPLICATION_PDF_VALUE);
            response.setHeader ("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream ().write (bytes);
        } else if (id != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findById (id));
            bytes = relatoriosServices.exportaPDFMonitorador (monitoradores);
            response.setContentType (MediaType.APPLICATION_PDF_VALUE);
            response.setHeader ("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream ().write (bytes);
        } else if (nome == null && cnpj == null && cpf == null && id == null) {
            List<Monitorador> monitoradores = services.findAll ();
            bytes = relatoriosServices.exportaPDFMonitorador (monitoradores);
            response.setContentType (MediaType.APPLICATION_PDF_VALUE);
            response.setHeader ("Content-disposition", "attachment; filename=relatorioMonitorador.pdf");
            response.getOutputStream ().write (bytes);
        }


    }

    @GetMapping("/relatorio/excel")
    public ResponseEntity<InputStreamResource> exportarMonitoradoresParaExcel(@RequestParam(required = false) String nome,
                                                                              @RequestParam(required = false) String cnpj,
                                                                              @RequestParam(required = false) String cpf,
                                                                              @RequestParam(required = false) Long id) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (nome != null) {
            List<Monitorador> monitoradores = services.findByNome (nome);
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel (monitoradores);
        } else if (cnpj != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findBycnpj (cnpj));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel (monitoradores);
        } else if (cpf != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findByCpf (cpf));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel (monitoradores);
        } else if (id != null) {
            List<Monitorador> monitoradores = Collections.singletonList (services.findById (id));
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel (monitoradores);
        } else if (nome == null && cnpj == null && cpf == null && id == null) {
            List<Monitorador> monitoradores = services.findAll ();
            byteArrayOutputStream = services.exportarMonitoradoresParaExcel (monitoradores);
        }

        HttpHeaders headers = new HttpHeaders ();
        headers.add ("Content-Disposition", "attachment; filename=monitoradores.xlsx");

        return ResponseEntity
                .ok ()
                .headers (headers)
                .contentType (MediaType.parseMediaType ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body (new InputStreamResource (new ByteArrayInputStream (byteArrayOutputStream.toByteArray ())));
    }


    @GetMapping("/relatorio/excelModelo")
    public ResponseEntity<InputStreamResource> ModeloExcel() {
        ByteArrayOutputStream byteArrayOutputStream = services.ModeloCadastro ();

        HttpHeaders headers = new HttpHeaders ();
        headers.add ("Content-Disposition", "attachment; filename=monitoradoresModelo.xlsx");
        return ResponseEntity
                .ok ()
                .headers (headers)
                .contentType (MediaType.parseMediaType ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body (new InputStreamResource (new ByteArrayInputStream (byteArrayOutputStream.toByteArray ())));
    }

    @PostMapping("/importar-excel")
    public ResponseEntity<?> importarMonitoradoresDoExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Monitorador> monitoradores = services.criarExcel (file);

            return ResponseEntity.ok ().build ();
        } catch (Exception e) {
            e.printStackTrace ();
            return ResponseEntity.badRequest ().body ("Erro durante a importação:"+ e);
        }
    }


    @GetMapping(value = "/filtroNome/{nome}")
    public ResponseEntity<List<Monitorador>> findByNome(@PathVariable String nome) {
        List<Monitorador> obj;

        if (nome.contains (" ")) {
            // Se o nome contém um espaço, considera o nome completo
            obj = services.findByNome (nome);
        } else {
            // Se não contém um espaço, considera o primeiro nome
            obj = services.findByNomeStartingWith (nome);
        }

        return ResponseEntity.ok ().body (obj);
    }


    @GetMapping(value = "/filtroCnpj/{cnpj}")
    public ResponseEntity<Monitorador> findBycnpj(@PathVariable String cnpj) {
        Monitorador obj = services.findBycnpj (cnpj);
        return ResponseEntity.ok ().body (obj);
    }

    @GetMapping(value = "/filtroCpf/{cpf}")
    public ResponseEntity<Monitorador> findByCpf(@PathVariable String cpf) {
        Monitorador obj = services.findByCpf (cpf);
        return ResponseEntity.ok ().body (obj);
    }


}
