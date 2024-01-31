package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.Repositorys.EnderecoRepositorys;
import com.desafioestagio.Projeto_Estagio.Repositorys.MonitoradorRepositorys;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.DataBaseExeception;
import com.desafioestagio.Projeto_Estagio.Services.exceptions.ResourceNotFoundException;
import com.desafioestagio.Projeto_Estagio.entities.Endereco;
import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
@CrossOrigin(origins = "http://localhost:4200")
public class MonitoradorServices {

    @Autowired
    private MonitoradorRepositorys repository;


    @Autowired
    private EnderecoRepositorys enderecoRepositorys;


    public List<Monitorador> findAll() {
        return repository.findAll ();
    }

    public Monitorador findById(Long id) {
        Optional<Monitorador> obj = repository.findById (id);
        return obj.orElseThrow (() -> new ResourceNotFoundException (id));
    }

    public List<Monitorador> findByNomeStartingWith(String Name) {
        return repository.findByNomeStartingWith (Name);

    }

    public List<Monitorador> findByNome(String Name) {
        return repository.findByNome (Name);

    }


    public Monitorador findBycnpj(String cnpj) {
        return repository.findBycnpj (cnpj);

    }

    public Monitorador findByCpf(String cpf) {
        return repository.findByCpf (cpf);

    }


    public Monitorador insert(Monitorador obj) {
        return repository.save (obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById (id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException (id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExeception (e.getMessage ());
        }
    }

    public Monitorador ultimo() {
        return repository.findTopByOrderByIdDesc ();
    }

    public Monitorador update(Long id, Monitorador obj) throws EntityNotFoundException {
        Monitorador entity = repository.getReferenceById (id);
        updateData (entity, obj);
        return repository.save (entity);
    }


    public boolean ValidadorIgualID(Monitorador obj) {
        if ("Juridica".equals (obj.getTipo ())) {
            return repository.existsByCnpj (obj.getCnpj ()) && repository.existsByInscricao (obj.getInscricao ()) && repository.existsByEmail (obj.getEmail ());
        } else {
            return repository.existsByCpf (obj.getCpf ()) && repository.existsByRg (obj.getRg ()) && repository.existsByEmail (obj.getEmail ());
        }
    }

    public boolean ValidadorIgualIDPUT(Monitorador obj) {
        if ("Juridica".equals (obj.getTipo ())) {
            return repository.existsByCnpjAndIdNot (obj.getCnpj (), obj.getId ()) && repository.existsByInscricaoAndIdNot (obj.getInscricao (), obj.getId ()) && repository.existsByEmailAndIdNot (obj.getEmail (), obj.getId ());
        } else {
            return repository.existsByCpfAndIdNot (obj.getCpf (), obj.getId ()) && repository.existsByRgAndIdNot (obj.getRg (), obj.getId ()) && repository.existsByEmailAndIdNot (obj.getEmail (),obj.getId ());
        }
    }


    public boolean ValidadorIgualIDEnd(Endereco obj) {
        return  enderecoRepositorys.existsByEndereco (obj.getEndereco ());
    }


    public ByteArrayOutputStream exportarMonitoradoresParaExcel(List<Monitorador> monitoradores) {
        try (Workbook workbook = new XSSFWorkbook (); ByteArrayOutputStream out = new ByteArrayOutputStream ()) {
            Sheet sheet = workbook.createSheet ("Monitoradores");

            // Crie o cabeçalho
            Row headerRow = sheet.createRow (0);
            headerRow.createCell (0).setCellValue ("Código");
            headerRow.createCell (1).setCellValue ("Tipo");
            headerRow.createCell (2).setCellValue ("CPF");
            headerRow.createCell (3).setCellValue ("CNPJ");
            headerRow.createCell (4).setCellValue ("Nome");
            headerRow.createCell (5).setCellValue ("Email");
            headerRow.createCell (6).setCellValue ("RG");
            headerRow.createCell (7).setCellValue ("Inscrição Estadual");
            headerRow.createCell (8).setCellValue ("Data Nascimento");
            headerRow.createCell (9).setCellValue ("Ativo");

            // Preencha os dados
            int rowNum = 1;
            for (Monitorador monitorador : monitoradores) {
                Row row = sheet.createRow (rowNum++);
                row.createCell (0).setCellValue (monitorador.getId ());
                row.createCell (1).setCellValue (monitorador.getTipo ());
                row.createCell (2).setCellValue (monitorador.getCpf ());
                row.createCell (3).setCellValue (monitorador.getCnpj ());
                row.createCell (4).setCellValue (monitorador.getNome ());
                row.createCell (5).setCellValue (monitorador.getEmail ());
                row.createCell (6).setCellValue (monitorador.getRg ());
                row.createCell (7).setCellValue (monitorador.getInscricao ());
                row.createCell (8).setCellValue (monitorador.getData_nascimento ());
                row.createCell (9).setCellValue (monitorador.isAtivo () ? "Sim" : "Não");
            }

            workbook.write (out);
            return out;

        } catch (IOException e) {
            e.printStackTrace ();
            // Lide com a exceção conforme necessário
            return null;
        }
    }


    //Modelo para cadastro
    public ByteArrayOutputStream ModeloCadastro() {
        try (Workbook workbook = new XSSFWorkbook (); ByteArrayOutputStream out = new ByteArrayOutputStream ()) {
            Sheet sheet = workbook.createSheet ("MonitoradoresModeloCadastro");

            // Crie o cabeçalho
            Row headerRow = sheet.createRow (0);
            headerRow.createCell (0).setCellValue ("Tipo");
            headerRow.createCell (1).setCellValue ("CPF");
            headerRow.createCell (2).setCellValue ("CNPJ");
            headerRow.createCell (3).setCellValue ("Nome");
            headerRow.createCell (4).setCellValue ("Email");
            headerRow.createCell (5).setCellValue ("RG");
            headerRow.createCell (6).setCellValue ("Inscrição Estadual");
            headerRow.createCell (7).setCellValue ("Data Nascimento");
            headerRow.createCell (8).setCellValue ("Ativo");
            workbook.write (out);
            return out;

        } catch (IOException e) {
            e.printStackTrace ();
            // Lide com a exceção conforme necessário
            return null;
        }
    }


    public List<Monitorador> criarExcel(MultipartFile file) throws Exception {
        List<Monitorador> monitoradores = new ArrayList<>();

        Logger logger = LoggerFactory.getLogger(getClass());

        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx")) {
            throw new IllegalArgumentException("Formato de arquivo inválido. Apenas arquivos XLSX são suportados.");
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getLastRowNum() < 1) {
                throw new IllegalArgumentException("A planilha está vazia ou contém apenas o cabeçalho.");
            }

            Set<String> colunasNecessarias = Set.of("Tipo", "CPF", "CNPJ", "Nome", "Email", "RG", "Inscrição Estadual", "Data Nascimento", "Ativo");
            Row headerRow = sheet.getRow(0);
            Set<String> colunasDoArquivo = new HashSet<>();
            for (Cell cell : headerRow) {
                colunasDoArquivo.add(cell.getStringCellValue());
            }

            if (!colunasDoArquivo.containsAll(colunasNecessarias)) {
                Set<String> colunasAusentes = new HashSet<>(colunasNecessarias);
                colunasAusentes.removeAll(colunasDoArquivo);

                throw new IllegalArgumentException("Colunas necessárias ausentes: " + colunasAusentes);
            }

            if (colunasDoArquivo != colunasNecessarias){
                throw new IllegalArgumentException("Excel fora do padrão:"+ "Experado: " + colunasNecessarias +"\n Arquivo:\n" + colunasDoArquivo);
            }


            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }



            if (!iterator.hasNext()) {
                throw new IllegalArgumentException("A planilha está vazia.");
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                Monitorador monitorador = new Monitorador();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            monitorador.setTipo(cell.getStringCellValue());
                            break;
                        case 1:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                monitorador.setCpf(String.valueOf((long) cell.getNumericCellValue()));
                            } else if (cell.getCellType() == CellType.STRING) {
                                monitorador.setCpf(cell.getStringCellValue());
                            } else {
                                // Tratar outros tipos, se necessário
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                monitorador.setCnpj(String.valueOf((long) cell.getNumericCellValue()));
                            } else if (cell.getCellType() == CellType.STRING) {
                                monitorador.setCnpj(cell.getStringCellValue());
                            } else {
                                // Tratar outros tipos, se necessário
                            }
                            break;
                        case 3:
                            monitorador.setNome(cell.getStringCellValue());
                            break;
                        case 4:
                            monitorador.setEmail(cell.getStringCellValue());
                            break;
                        case 5:
                            monitorador.setRg(cell.getStringCellValue());
                            break;
                        case 6:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                monitorador.setInscricaol(String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0)));
                            } else if (cell.getCellType() == CellType.STRING) {
                                monitorador.setInscricaol(cell.getStringCellValue());
                            } else {
                                // Tratar outros tipos, se necessário
                            }
                            break;
                        case 7:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                monitorador.setData_nascimento(String.valueOf(cell.getNumericCellValue()));
                            } else if (cell.getCellType() == CellType.STRING) {
                                monitorador.setData_nascimento(cell.getStringCellValue());
                            } else {
                                // Tratar outros tipos, se necessário
                            }
                            break;
                        case 8:
                            if (cell.getCellType() == CellType.STRING) {
                                String ativoString = cell.getStringCellValue();
                                monitorador.setAtivo(converterStringParaBooleano(ativoString));
                            }
                            break;
                        // Adicione outros casos conforme necessário

                        default:
                            logger.warn("Ignorando coluna desconhecida: {}", columnIndex);
                    }
                }

                insert(monitorador);
                monitoradores.add(monitorador);
            }

        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo Excel", e);
            throw new RuntimeException("Erro ao ler o arquivo Excel", e);
        }

        return monitoradores;
    }

    private boolean isXLSXFormat(InputStream inputStream) {
        try (POIFSFileSystem fs = new POIFSFileSystem (inputStream)) {
            DirectoryEntry root = fs.getRoot ();
            for (Entry entry : root) {
                if (entry.getName ().equalsIgnoreCase ("[Content_Types].xml")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace (); // Tratar exceções adequadamente
        }
        return false;
    }

    public boolean converterStringParaBooleano(String valorString) {
        return "Sim".equalsIgnoreCase (valorString);
    }


    public void updateData(Monitorador entity, Monitorador obj) {
        entity.setNome (obj.getNome ());
        entity.setEmail (obj.getEmail ());
        entity.setInscricaol (obj.getInscricao ());
        entity.setAtivo (obj.isAtivo ());
        entity.setRg (obj.getRg ());
        entity.setCpf (obj.getCpf ());
        entity.setCnpj (obj.getCnpj ());
        entity.setData_nascimento (obj.getData_nascimento ());
    }


}
