package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.MonitoradorRepositorys;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.DataBaseExeception;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.ResourceNotFoundException;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public List<Monitorador>findByNome( String Name){
        return repository.findByNomeStartingWith(Name);

    }


    public Monitorador findBycnpj( String cnpj){
        return repository.findBycnpj(cnpj);

    }

    public Monitorador findByCpf (String cpf){
        return repository.findByCpf(cpf);

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


    public ByteArrayOutputStream exportarMonitoradoresParaExcel() {
        List<Monitorador> monitoradores = repository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Monitoradores");

            // Crie o cabeçalho
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Código");
            headerRow.createCell(1).setCellValue("Tipo");
            headerRow.createCell(2).setCellValue("CPF");
            headerRow.createCell(3).setCellValue("CNPJ");
            headerRow.createCell(4).setCellValue("Nome");
            headerRow.createCell(5).setCellValue("Email");
            headerRow.createCell(6).setCellValue("RG");
            headerRow.createCell(7).setCellValue("Inscrição Estadual");
            headerRow.createCell(8).setCellValue("Data Nascimento");
            headerRow.createCell(9).setCellValue("Ativo");

            // Preencha os dados
            int rowNum = 1;
            for (Monitorador monitorador : monitoradores) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(monitorador.getId());
                row.createCell(1).setCellValue(monitorador.getTipo());
                row.createCell(2).setCellValue(monitorador.getCpf());
                row.createCell(3).setCellValue(monitorador.getCnpj());
                row.createCell(4).setCellValue(monitorador.getNome());
                row.createCell(5).setCellValue(monitorador.getEmail());
                row.createCell(6).setCellValue(monitorador.getRg());
                row.createCell(7).setCellValue(monitorador.getinscricao());
                row.createCell(8).setCellValue(monitorador.getData_nascimento());
                row.createCell(9).setCellValue(monitorador.isAtivo() ? "Sim" : "Não");
            }

            workbook.write(out);
            return out;

        } catch (IOException e) {
            e.printStackTrace();
            // Lide com a exceção conforme necessário
            return null;
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
