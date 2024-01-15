package com.projetounika.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetounika.entities.Endereco;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class EnderecoHttpClient implements Serializable {

    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public EnderecoHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Endereco> listarTodos() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(baseUrl);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            httpClient.close();
            response.close();

            return objectMapper.readValue(responseString, new TypeReference<List<Endereco>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Endereco BuscaCep(String cep) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(baseUrl + cep);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            httpClient.close();
            response.close();

            return objectMapper.readValue(responseString, new TypeReference<Endereco>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    public Endereco Criar(Endereco endereco, Long id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(baseUrl + "/" + id + "/" + "enderecos");
        String json = objectMapper.writeValueAsString(endereco);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpClient.execute(post);

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseMessage = EntityUtils.toString(response.getEntity());

            if (statusCode >= 200 && statusCode < 300) {
                // Sucesso
                return objectMapper.readValue(responseMessage, new TypeReference<Endereco>() {});
            } else {
                // Erro
                throw new HttpResponseException (statusCode, responseMessage);
            }
        } finally {
            httpClient.close();
            response.close();
        }
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




    public Monitorador Atualizar(Endereco endereco) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpPut put = new HttpPut(baseUrl + "/" + endereco.getId());
            String json = objectMapper.writeValueAsString(endereco);
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
                throw new IOException("Falha ao adicionar/atualizar o endereco Código de resposta: " + statusCode);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }


}
