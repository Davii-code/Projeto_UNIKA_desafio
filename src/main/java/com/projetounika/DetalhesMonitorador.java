package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.entities.Monitorador;
import com.projetounika.services.EnderecoHttpClient;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.List;

public class DetalhesMonitorador extends WebPage {
    // Construtor padrão sem argumentos
    public DetalhesMonitorador(){}

    // Construtor principal
    public DetalhesMonitorador(Monitorador monitorador ) {

        Endereco endereco = new Endereco();

        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {
            @Override
            protected void onSubmit() {
            }
        };
        form.add(new TextField<>("id"));
        form.add(new TextField<>("nome"));
        form.add(new TextField<>("cpf"));
        form.add(new TextField<>("email"));
        form.add(new TextField<>("rg"));
        form.add(new Select<>("tipo"));
        form.add(new Select<>("Ativo", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return monitorador.isAtivo() ? "Sim" : "Não";
            }
        }));
        add(form);

        EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/monitorador/"+monitorador.getId()+"/enderecos");
        List<Endereco> enderecoList = enderecoHttpClient.listarTodos();
        final  CompoundPropertyModel<List<Endereco>> enderecoListModel = new CompoundPropertyModel<>(enderecoList);

        final ListView<Endereco> enderecoListView = new ListView<Endereco>("end",enderecoListModel) {
            @Override
            protected void populateItem(ListItem<Endereco> listItem) {
                final Endereco endereco = listItem.getModelObject();
                listItem.add(new Label("id",endereco.getId()));
                listItem.add(new Label("en",endereco.getEndereco()));
                listItem.add(new Label("num",endereco.getNumero()));
                listItem.add(new Label("cep",endereco.getCep()));
                listItem.add(new Label("est",endereco.getEstado()));
                listItem.add(new Label("cid",endereco.getCidade()));
                listItem.add(new Label("principal",endereco.getPrincipal() ? "Sim" : "Não") );


                listItem.add(new Link<Void>("detalhesEnd") {

                    @Override
                    public void onClick() {
                        setResponsePage(new DetalhesEndereco(endereco));
                    }
                });

            }
        };
        add(enderecoListView);
    }
}





