package com.projetounika.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetounika.entities.Monitorador;
import org.apache.flink.fs.azure.shaded.org.apache.http.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitoradorHttpClient implements Serializable {

    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public MonitoradorHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Monitorador> listarTodos() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(baseUrl);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            httpClient.close();
            response.close();

            return objectMapper.readValue(responseString, new TypeReference<List<Monitorador>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Monitorador listarPorID() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(baseUrl);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            httpClient.close();
            response.close();

            return objectMapper.readValue(responseString, new TypeReference<Monitorador>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deletar(Long id) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpDelete delete = new HttpDelete(baseUrl + "/" + id);
            CloseableHttpResponse response = httpClient.execute(delete);

            try {
                int statusCode = response.getStatusLine().getStatusCode();
                return statusCode;
            } finally {
                response.close();
            }
        } catch (IOException e) {
            // Imprima ou logue a exceção para diagnóstico
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar a requisição DELETE", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // Imprima ou logue a exceção para diagnóstico
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar o cliente HTTP", e);
            }
        }
    }


    public Monitorador Atualizar(Monitorador monitorador) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpPut put = new HttpPut(baseUrl + "/" + monitorador.getId());
            String json = objectMapper.writeValueAsString(monitorador);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

            put.setEntity(entity);
            put.setHeader("Content-type", "application/json");

            response = httpClient.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
                HttpEntity responseEntity = response.getEntity();
                String obj = EntityUtils.toString(responseEntity);
                return objectMapper.readValue(obj, new TypeReference<Monitorador>() {});
            } else {
                // Trate casos em que a requisição não foi bem-sucedida
                throw new IOException("Falha ao adicionar/atualizar o Monitorador. Código de resposta: " + statusCode);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }


    public Monitorador Criar(Monitorador monitorador) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(baseUrl);
        String json = objectMapper.writeValueAsString(monitorador);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpClient.execute(post);

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseMessage = EntityUtils.toString(response.getEntity());


            // Sucesso
            return objectMapper.readValue(responseMessage, new TypeReference<Monitorador>() {});

        } finally {
            httpClient.close();
            response.close();
        }
    }


    public List<Monitorador> criarExcel(File excel) {
        List<Monitorador> monitoradores = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(excel);
             Workbook workbook = new XSSFWorkbook (file)) {

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getLastRowNum() < 1) {
                System.out.println("A planilha está vazia ou contém apenas o cabeçalho.");
                return monitoradores;
            }
            Iterator<Row> iterator = sheet.iterator();



            // Pular o cabeçalho

            if (iterator.hasNext()) {
                iterator.next(); // Avança para a próxima linha (cabeçalho)
            }

// Verifica se há pelo menos uma linha de dados
            if (!iterator.hasNext()) {
                System.out.println("A planilha não contém dados.");
                return monitoradores;
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
                            monitorador.setCpf(cell.getStringCellValue());
                            break;
                        case 2:
                            monitorador.setCnpj(cell.getStringCellValue());
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
                            monitorador.setInscricao (String.valueOf (BigDecimal.valueOf (cell.getNumericCellValue ()).setScale (0)));
                            break;
                        case 7:

                            monitorador.setData_nascimento(String.valueOf (cell.getDateCellValue ()));
                            break;
                        case 8:
                            if (cell.getCellType() == CellType.STRING) {
                                String ativoString = cell.getStringCellValue();
                                monitorador.setAtivo(converterStringParaBooleano(ativoString));
                            }
                            break;


                        default:
                            System.out.println("");
                    }

                }
                Criar(monitorador);
            }

        } catch (IOException e) {
            e.printStackTrace(); // Tratar exceções adequadamente
        }

        return monitoradores;
    }


    public boolean converterStringParaBooleano(String valorString) {
        return "Sim".equalsIgnoreCase(valorString);
    }



}







