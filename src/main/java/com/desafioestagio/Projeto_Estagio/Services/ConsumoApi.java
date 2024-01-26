package com.desafioestagio.Projeto_Estagio.Services;

import com.desafioestagio.Projeto_Estagio.entities.EntitiesApiViaCep;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class ConsumoApi {


    private RestTemplate restTemplate;
    public ConsumoApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public EntitiesApiViaCep obterDados(String cep) {
        String url = UriComponentsBuilder.fromHttpUrl("https://viacep.com.br/ws/")
                .path(cep)
                .path("/json/")
                .toUriString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }



        URI uri = URI.create(url);

        // Correção aqui
        return restTemplate.getForObject(uri, EntitiesApiViaCep.class);

    }
}
