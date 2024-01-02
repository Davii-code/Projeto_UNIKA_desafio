package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.EnderecoRepositorys;
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
import java.io.IOException;
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
        return obj.orElseThrow(()->new ResourceNotFoundException(id));
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

    public Endereco update(Long id, Endereco obj){
        try {
            Endereco entity = enderecoRepositorys.getReferenceById(id);
            updateData(entity, obj);
            return enderecoRepositorys.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public ByteArrayOutputStream exportaEnderecoParaExcel(){
        List<Endereco> enderecos = enderecoRepositorys.findAll();
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()){
            Sheet sheet = workbook.createSheet("Endereco");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Código");
            headerRow.createCell(1).setCellValue("Enderecço");
            headerRow.createCell(2).setCellValue("Número");
            headerRow.createCell(3).setCellValue("CEP");
            headerRow.createCell(4).setCellValue("Telefone");
            headerRow.createCell(5).setCellValue("Cidade");
            headerRow.createCell(6).setCellValue("Estado");
            headerRow.createCell(7).setCellValue("Principal");



            int rowNum = 1;
            for (Endereco endereco : enderecos){
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(endereco.getId());
                row.createCell(1).setCellValue(endereco.getEndereco());
                row.createCell(2).setCellValue(endereco.getNumero());
                row.createCell(3).setCellValue(endereco.getCep());
                row.createCell(4).setCellValue(endereco.getTelefone());
                row.createCell(5).setCellValue(endereco.getCidade());
                row.createCell(6).setCellValue(endereco.getEstado());
                row.createCell(7).setCellValue(endereco.getPrincipal() ? "Sim" : "Não");

            }
            workbook.write(out);
            return out;
        } catch (IOException e) {
           e.printStackTrace();
            return null;
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
