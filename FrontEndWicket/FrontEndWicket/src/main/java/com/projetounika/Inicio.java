package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;



import java.util.List;


import org.apache.wicket.model.CompoundPropertyModel;



public class Inicio extends WebPage {
    public Inicio() {
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");

        // Obtém a lista de monitoradores usando o HttpClient
        List<Monitorador> list = monitoradorHttpClient.listarTodos();

        // Cria um modelo para a lista usando CompoundPropertyModel
        final CompoundPropertyModel<List<Monitorador>> monitoradorListModel = new CompoundPropertyModel<>(list);

        // Cria um ListView usando a lista de monitoradores
        final ListView<Monitorador> monitoradorListView = new ListView<Monitorador>("monitorador", monitoradorListModel) {
            @Override
            protected void populateItem(ListItem<Monitorador> listItem) {
                final Monitorador monitorador = listItem.getModelObject();
                listItem.add(new Label("id", monitorador.getId()));
                listItem.add(new Label("nome", monitorador.getNome()));
                listItem.add(new Label("CNPJ", monitorador.getCnpj()));
                listItem.add(new Label("CPF", monitorador.getCpf()));
                listItem.add(new Label("ativo", monitorador.isAtivo() ? "Sim":"Não"));

            }
        };

        add(monitoradorListView);
    }
}

