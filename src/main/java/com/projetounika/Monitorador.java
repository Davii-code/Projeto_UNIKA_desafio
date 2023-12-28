package com.projetounika;

import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;
import com.projetounika.services.MonitoradorHttpClient;
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


import java.util.List;


public class Monitorador extends WebPage {


    public Monitorador() {
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        Endereco endereco = new Endereco();
        //Modal
        final ModalWindow modal = new ModalWindow("modal");
        modal.setInitialHeight(350);
        modal.setInitialWidth(850);
        add(modal);




        // Obtém a lista de monitoradores usando o HttpClient
        List<com.projetounika.entities.Monitorador> list = monitoradorHttpClient.listarTodos();


        // Cria um modelo para a lista usando CompoundPropertyModel
        final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>> monitoradorListModel = new CompoundPropertyModel<>(list);


        // Cria um ListView usando a lista de monitoradores
        final ListView<com.projetounika.entities.Monitorador> monitoradorListView = new ListView<com.projetounika.entities.Monitorador>("monitorador", monitoradorListModel) {
            @Override
            protected void populateItem(ListItem<com.projetounika.entities.Monitorador> listItem) {
                final com.projetounika.entities.Monitorador monitorador = listItem.getModelObject();
                listItem.add(new Label("id", monitorador.getId()));
                listItem.add(new Label("nome", monitorador.getNome()));
                listItem.add(new Label("CPF", monitorador.getCpf()));
                listItem.add(new Label("CNPJ", monitorador.getCnpj()));
                listItem.add(new Label("ativo", monitorador.isAtivo() ? "Sim" : "Não"));




                EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/monitorador/"+monitorador.getId()+"/enderecos");
                List<Endereco> enderecoList = enderecoHttpClient.listarTodos();
                final  CompoundPropertyModel<List<Endereco>> enderecoListModel = new CompoundPropertyModel<>(enderecoList);
                listItem.add(new AjaxLink<Void>("editarLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new DetalhesMonitorador(listItem.getModelObject(),modal.getContentId(),modal));
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

                        modal.setContent(new DeleteMonitorador(monitorador,modal.getContentId(),modal));
                        modal.show(target);

                    }

                });

            }
        };
        modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(Monitorador.class);
            }
        });
        add(monitoradorListView);


        ExternalLink linkPdf = new ExternalLink("pdf","http://localhost:8080/monitorador/relatorio/pdfs");
        add(linkPdf);

        AjaxLink<Void> linkCriarPessoaJuridica = new AjaxLink<Void>("linkCriarJ") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modal.setContent(new CadastroMonitorador(modal.getContentId(),modal,"Juridica"));
                modal.show(ajaxRequestTarget);
            }
        };

        add(linkCriarPessoaJuridica);

        AjaxLink<Void> linkCriarPessoaFisica = new AjaxLink<Void>("linkCriarF") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modal.setContent(new CadastroMonitorador(modal.getContentId(),modal,"Fisica"));
                modal.show(ajaxRequestTarget);
            }
        };

        add(linkCriarPessoaFisica);
    }
}