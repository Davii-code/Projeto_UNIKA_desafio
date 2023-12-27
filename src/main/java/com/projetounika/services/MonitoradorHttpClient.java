package com.projetounika.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetounika.entities.Monitorador;
import org.apache.flink.fs.azure.shaded.org.apache.http.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.*;

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
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
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


    public Monitorador Criar (Monitorador monitorador) throws IOException {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost (baseUrl);
            String json = objectMapper.writeValueAsString(monitorador);
            StringEntity entity = new StringEntity(json,ContentType.APPLICATION_JSON);

            post.setEntity(entity);
            post.setHeader("Content-type","application/json");

            CloseableHttpResponse response = httpClient.execute(post);

            HttpEntity httpEntity = response.getEntity();
            String obj = EntityUtils.toString(httpEntity);

            httpClient.close();
            response.close();

            return objectMapper.readValue(obj, new TypeReference<Monitorador>(){});


    }




}







