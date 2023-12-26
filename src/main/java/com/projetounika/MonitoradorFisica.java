package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.entities.Monitorador;
import com.projetounika.services.EnderecoHttpClient;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class MonitoradorFisica extends WebPage {


    public MonitoradorFisica() {
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador/PessoaFisica");
        Endereco endereco = new Endereco();
        //Modal
        final ModalWindow modal = new ModalWindow("modal");
        modal.setInitialHeight(700);
        modal.setInitialWidth(850);
        add(modal);


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
                listItem.add(new Label("CPF", monitorador.getCpf()));
                listItem.add(new Label("ativo", monitorador.isAtivo() ? "Sim" : "Não"));




                EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/monitorador/"+monitorador.getId()+"/enderecos");
                List<Endereco> enderecoList = enderecoHttpClient.listarTodos();
                final  CompoundPropertyModel<List<Endereco>> enderecoListModel = new CompoundPropertyModel<>(enderecoList);
                listItem.add(new AjaxLink<Void>("editarLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new DetalhesMonitoradorFisica(listItem.getModelObject(),modal.getContentId(),modal));
                        modal.show(target);

                    }

                });
                listItem.add(new AjaxLink<Void>("editarLinkEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new DetalhesEndereco(enderecoListModel,modal.getContentId(),modal));
                        modal.show(target);

                    }

                });


                listItem.add(new AjaxLink<Void>("LinkDeletar") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new DeleteMonitoradorFisica(monitorador,modal.getContentId(),modal));
                        modal.show(target);

                    }

                });

            }
        };
        modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(MonitoradorFisica.class);
            }
        });
        add(monitoradorListView);
        Link<Void> link = new Link<Void>("Telajuridica") {
            @Override
            public void onClick() {
                setResponsePage(MonitoradorJuridica.class);
            }
        };
        add(link);

        ExternalLink linkPdf = new ExternalLink("pdf","http://localhost:8080/monitorador/relatorio/pdfs/PessoaFisica");
        add(linkPdf);


    }
}

